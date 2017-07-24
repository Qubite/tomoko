package io.qubite.tomoko.operation;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.specification.TreeSpecification;

public interface OperationExecutor {

    void execute(TreeSpecification patchSpecification, OperationDto operation);

    void execute(TreeSpecification patchSpecification, Patch operations);

}
