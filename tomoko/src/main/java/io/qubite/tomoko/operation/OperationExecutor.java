package io.qubite.tomoko.operation;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.specification.PatcherSpecification;

public interface OperationExecutor {

    void execute(PatcherSpecification patchSpecification, OperationDto operation);

    void execute(PatcherSpecification patchSpecification, Patch operations);

}
