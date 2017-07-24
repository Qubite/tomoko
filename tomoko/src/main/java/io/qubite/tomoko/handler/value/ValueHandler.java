package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;

/**
 * Created by edhendil on 19.07.17.
 */
public interface ValueHandler {

    void execute(Path path, ValueTree value);

}
