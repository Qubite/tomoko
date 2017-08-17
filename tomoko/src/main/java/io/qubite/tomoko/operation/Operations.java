package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;

public class Operations {

    private Operations() {
    }

    public static ValueOperation value(Path path, ValueHandler handler, ValueTree value) {
        return new ValueOperation(path, handler, value);
    }

    public static ValuelessOperation valueless(Path path, ValuelessHandler handler) {
        return new ValuelessOperation(path, handler);
    }

}
