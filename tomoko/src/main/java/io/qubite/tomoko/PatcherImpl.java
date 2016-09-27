package io.qubite.tomoko;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.operation.OperationExecutor;
import io.qubite.tomoko.specification.TreeSpecification;

/**
 * Created by edhendil on 29.08.16.
 */
class PatcherImpl implements Patcher {

    private final TreeSpecification treeSpecification;
    private final OperationExecutor operationExecutor;

    PatcherImpl(TreeSpecification treeSpecification, OperationExecutor operationExecutor) {
        this.treeSpecification = treeSpecification;
        this.operationExecutor = operationExecutor;
    }

    public void execute(Patch patch) {
        operationExecutor.execute(treeSpecification, patch);
    }

    public void execute(OperationDto operation) {
        operationExecutor.execute(treeSpecification, operation);
    }

}
