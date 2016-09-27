package io.qubite.tomoko.operation;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.specification.TreeSpecification;

/**
 * Created by edhendil on 20.08.16.
 */
public interface OperationExecutor {

    void execute(TreeSpecification patchSpecification, OperationDto operation);

    void execute(TreeSpecification patchSpecification, Patch operations);

}
