package io.qubite.tomoko.operation;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.format.TreeTextFormat;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.tree.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationExecutorImpl implements OperationExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExecutorImpl.class);

    private final HandlerResolver handlerResolver;

    public OperationExecutorImpl(HandlerResolver handlerResolver) {
        this.handlerResolver = handlerResolver;
    }

    public void execute(PatcherSpecification patchSpecification, OperationDto operation) {
        LOGGER.debug("Executing operation {}", operation);
        try {
            handleOperation(patchSpecification, operation);
        } catch (PatcherException e) {
            throw new PatcherException(String.format("Error handling an operation: %s", operation), e);
        }
    }

    public void execute(PatcherSpecification patchSpecification, Patch operations) {
        try {
            for (OperationDto operation : operations.getOperations()) {
                execute(patchSpecification, operation);
            }
        } catch (PatcherException e) {
            throw new PatcherException(String.format("Error handling a patch"), e);
        }
    }

    private void handleOperation(PatcherSpecification specification, OperationDto operation) {
        Path path = Path.parse(operation.getPath());
        switch (operation.getType()) {
            case ADD:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("For the tree\n{}", new TreeTextFormat().addHandlersToString(specification));
                }
                parseAddOperation(specification.getAddHandlerTree(), path, operation.getValue()).stream().forEach(Operation::execute);
                break;
            case REMOVE:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("For the tree\n{}", new TreeTextFormat().removeHandlersToString(specification));
                }
                parseRemoveOperation(specification.getRemoveHandlerTree(), path).execute();
                break;
            case REPLACE:
                parseReplaceOperation(specification.getReplaceHandlerTree(), path, operation.getValue()).execute();
                break;
            default:
                throw new PatcherException("Unsupported operation type.");
        }
    }

    private List<ValueOperation> parseAddOperation(Tree<ValueHandler> addHandlerTree, Path path, ValueTree value) {
        return handlerResolver.findValueHandlers(addHandlerTree, path, value);
    }

    private ValuelessOperation parseRemoveOperation(Tree<ValuelessHandler> removeHandlerTree, Path path) {
        return handlerResolver
                .findValuelessHandler(removeHandlerTree, path);
    }

    private ValueOperation parseReplaceOperation(Tree<ValueHandler> replaceHandlerTree, Path path, ValueTree value) {
        return handlerResolver.findValueHandler(replaceHandlerTree, path, value);
    }

}
