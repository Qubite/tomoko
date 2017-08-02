package io.qubite.tomoko.patcher;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;

public interface Patcher {

    void execute(OperationDto operation);

    void execute(Patch operations);

}
