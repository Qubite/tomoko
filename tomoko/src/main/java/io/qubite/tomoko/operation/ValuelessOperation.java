package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.Path;

public class ValuelessOperation implements Operation {

    private final Path path;
    private final ValuelessHandler handler;

    ValuelessOperation(Path path, ValuelessHandler handler) {
        this.path = path;
        this.handler = handler;
    }

    public static ValuelessOperation of(Path path, ValuelessHandler handler) {
        return new ValuelessOperation(path, handler);
    }

    @Override
    public void execute() {
        handler.execute(path);
    }

}
