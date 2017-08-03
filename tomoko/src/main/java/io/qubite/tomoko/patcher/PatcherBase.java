package io.qubite.tomoko.patcher;

import io.qubite.tomoko.operation.OperationExecutor;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.resolver.HandlerResolver;

public class PatcherBase implements Patcher {

    private final HandlerResolver handlerResolver;
    private final OperationExecutor operationExecutor;

    PatcherBase(HandlerResolver handlerResolver, OperationExecutor operationExecutor) {
        this.handlerResolver = handlerResolver;
        this.operationExecutor = operationExecutor;
    }

    public static PatcherBase instance(HandlerResolver patcherTreeSpecification, OperationExecutor operationExecutor) {
        return new PatcherBase(patcherTreeSpecification, operationExecutor);
    }

    public void execute(Patch patch) {
        operationExecutor.execute(handlerResolver, patch);
    }

    public void execute(OperationDto operation) {
        operationExecutor.execute(handlerResolver, operation);
    }

}
