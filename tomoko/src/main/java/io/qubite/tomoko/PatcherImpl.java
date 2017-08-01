package io.qubite.tomoko;

import io.qubite.tomoko.operation.OperationExecutor;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.specification.PatcherSpecification;

public class PatcherImpl implements Patcher {

    private final PatcherSpecification patcherSpecification;
    private final OperationExecutor operationExecutor;

    PatcherImpl(PatcherSpecification patcherSpecification, OperationExecutor operationExecutor) {
        this.patcherSpecification = patcherSpecification;
        this.operationExecutor = operationExecutor;
    }

    public static PatcherImpl instance(PatcherSpecification patcherSpecification, OperationExecutor operationExecutor) {
        return new PatcherImpl(patcherSpecification, operationExecutor);
    }

    public void execute(Patch patch) {
        operationExecutor.execute(patcherSpecification, patch);
    }

    public void execute(OperationDto operation) {
        operationExecutor.execute(patcherSpecification, operation);
    }

}
