package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;

public interface ValueHandler {

    void execute(Path path, ValueTree value);

}
