package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.HandlerException;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.resolver.HandlerNotFoundException;
import io.qubite.tomoko.resolver.HandlerResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationExecutorImpl implements OperationExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExecutorImpl.class);

    public void execute(HandlerResolver handlerResolver, Patch operations) {
        for (OperationDto operation : operations.getOperations()) {
            execute(handlerResolver, operation);
        }
    }

    public void execute(HandlerResolver handlerResolver, OperationDto operation) throws InvalidOperationException {
        LOGGER.debug("Executing operation {}", operation);
        try {
            handleOperation(handlerResolver, operation);
        } catch (HandlerNotFoundException e) {
            throw new InvalidOperationException(operation, e);
        } catch (HandlerException e) {
            throw new InvalidOperationException(operation, e);
        }
    }

    private void handleOperation(HandlerResolver handlerResolver, OperationDto operationDto) {
        Path path = Path.parse(operationDto.getPath());
        switch (operationDto.getType()) {
            case ADD:
                LOGGER.debug("Looking for ADD handlers starting at {}", path);
                List<ValueOperation> foundOperations = handlerResolver.findAddHandlers(path, operationDto.getValue());
                for (ValueOperation operation : foundOperations) {
                    operation.execute();
                }
                break;
            case REMOVE:
                LOGGER.debug("Looking for REMOVE handler at {}", path);
                handlerResolver.findRemoveHandler(path).execute();
                break;
            case REPLACE:
                LOGGER.debug("Looking for REPLACE handler at {}", path);
                handlerResolver.findReplaceHandler(path, operationDto.getValue()).execute();
                break;
            default:
                throw new InvalidOperationException(operationDto);
        }
    }

}
