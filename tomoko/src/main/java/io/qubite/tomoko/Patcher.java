package io.qubite.tomoko;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;

public interface Patcher {

    void execute(OperationDto operation);

    void execute(Patch operations);

}
