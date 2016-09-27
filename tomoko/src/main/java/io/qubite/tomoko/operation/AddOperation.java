package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.path.PathParameters;

public class AddOperation<T> implements Operation {

    private final PathParameters pathParameters;
    private final T value;
    private final AddHandler<T> handler;

    AddOperation(PathParameters pathParameters, T value, AddHandler<T> handler) {
        this.pathParameters = pathParameters;
        this.value = value;
        this.handler = handler;
    }

    public void execute() {
        handler.execute(pathParameters, value);
    }

}
