package io.qubite.tomoko.operation;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.PatcherSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                LOGGER.debug("Looking for ADD handlers starting at {}", path);
                handlerResolver.findValueHandlers(specification.getAddHandlerTree(), path, operation.getValue()).stream().forEach(Operation::execute);
                break;
            case REMOVE:
                LOGGER.debug("Looking for REMOVE handler at {}", path);
                handlerResolver
                        .findValuelessHandler(specification.getRemoveHandlerTree(), path).execute();
                break;
            case REPLACE:
                LOGGER.debug("Looking for REPLACE handler at {}", path);
                handlerResolver.findValueHandler(specification.getReplaceHandlerTree(), path, operation.getValue()).execute();
                break;
            default:
                throw new PatcherException("Unsupported operation type.");
        }
    }

}
