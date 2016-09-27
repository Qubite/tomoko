package io.qubite.tomoko.operation;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.format.TreeTextFormat;
import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.resolver.AddOperationContext;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.resolver.RemoveOperationContext;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.type.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by edhendil on 17.08.16.
 */
public class OperationExecutorImpl implements OperationExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExecutorImpl.class);

    private final HandlerResolver handlerResolver;

    public OperationExecutorImpl(HandlerResolver handlerResolver) {
        this.handlerResolver = handlerResolver;
    }

    public void execute(TreeSpecification patchSpecification, OperationDto operation) {
        LOGGER.debug("Executing operation {}", operation);
        try {
            handleOperation(patchSpecification, operation);
        } catch (PatcherException e) {
            throw new PatcherException(String.format("Error handling an operation: %s", operation), e);
        }
    }

    public void execute(TreeSpecification patchSpecification, Patch operations) {
        for (OperationDto operation : operations.getOperations()) {
            execute(patchSpecification, operation);
        }
    }

    private void handleOperation(TreeSpecification specification, OperationDto operation) {
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
            default:
                throw new PatcherException("Unsupported operation type.");
        }
    }

    private List<AddOperation<?>> parseAddOperation(Tree<AddHandler<?>> addHandlerTree, Path path, JsonTree value) {
        return handlerResolver.findAddHandlers(addHandlerTree, path, value).stream().map(this::toAddOperation).collect(Collectors.toList());
    }

    private <T> AddOperation<T> toAddOperation(AddOperationContext<T> context) {
        ValueType<T> parameterClass = context.getHandler().getParameterClass();
        T parsedParameter = context.getValue().getAs(parameterClass);
        return Operations.add(context.getPathParameters(), parsedParameter, context.getHandler());
    }

    private RemoveOperation parseRemoveOperation(Tree<RemoveHandler> removeHandlerTree, Path path) {
        RemoveOperationContext matchingPath = handlerResolver
                .findRemoveHandler(removeHandlerTree, path);
        return Operations.remove(matchingPath.getPathParameters(), matchingPath.getHandler());
    }

}
