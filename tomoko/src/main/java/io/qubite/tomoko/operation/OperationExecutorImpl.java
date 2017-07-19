package io.qubite.tomoko.operation;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.format.TreeTextFormat;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.resolver.ValueOperationContext;
import io.qubite.tomoko.resolver.ValuelessOperationContext;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.type.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

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
        try {
            for (OperationDto operation : operations.getOperations()) {
                execute(patchSpecification, operation);
            }
        } catch (PatcherException e) {
            throw new PatcherException(String.format("Error handling a patch"), e);
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
            case REPLACE:
                parseReplaceOperation(specification.getReplaceHandlerTree(), path, operation.getValue()).execute();
                break;
            default:
                throw new PatcherException("Unsupported operation type.");
        }
    }

    private List<ValueOperation<?>> parseAddOperation(Tree<ValueHandler<?>> addHandlerTree, Path path, JsonTree value) {
        return handlerResolver.findValueHandlers(addHandlerTree, path, value).stream().map(this::toValueOperation).collect(Collectors.toList());
    }

    private <T> ValueOperation<T> toValueOperation(ValueOperationContext<T> context) {
        ValueType<T> parameterClass = context.getHandler().getParameterClass();
        T parsedValue = context.getValue().getAs(parameterClass);
        return Operations.value(context.getPathParameters(), parsedValue, context.getHandler());
    }

    private ValuelessOperation parseRemoveOperation(Tree<ValuelessHandler> removeHandlerTree, Path path) {
        ValuelessOperationContext matchingPath = handlerResolver
                .findValuelessHandler(removeHandlerTree, path);
        return Operations.valueless(matchingPath.getPathParameters(), matchingPath.getHandler());
    }

    private ValueOperation<?> parseReplaceOperation(Tree<ValueHandler<?>> replaceHandlerTree, Path path, JsonTree value) {
        ValueOperationContext<?> matchingPath = handlerResolver.findValueHandler(replaceHandlerTree, path, value);
        return toValueOperation(matchingPath);
    }

}
