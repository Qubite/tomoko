package io.qubite.tomoko;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;

/**
 * Created by edhendil on 30.08.16.
 */
public interface Patcher {

    void execute(OperationDto operation);

    void execute(Patch operations);

}
