package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.path.PathParameters;

public class AddOperation<T> implements Operation {

    private final PathParameters pathParameters;
    private final T value;
    private final ValueHandler<T> handler;

    AddOperation(PathParameters pathParameters, T value, ValueHandler<T> handler) {
        this.pathParameters = pathParameters;
        this.value = value;
        this.handler = handler;
    }

    public void execute() {
        handler.execute(pathParameters, value);
    }

}
