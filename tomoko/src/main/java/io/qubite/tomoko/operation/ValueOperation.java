package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;

public class ValueOperation implements Operation {

    private final Path path;
    private final ValueHandler handler;
    private final ValueTree value;

    ValueOperation(Path path, ValueHandler handler, ValueTree value) {
        this.path = path;
        this.handler = handler;
        this.value = value;
    }

    public static ValueOperation of(Path path, ValueHandler handler, ValueTree value) {
        return new ValueOperation(path, handler, value);
    }

    @Override
    public void execute() {
        handler.execute(path, value);
    }

}
