package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathParameters;

public class Operations {

    private Operations() {
    }

    public static <T> AddOperation<T> add(PathParameters pathParameters, T value, ValueHandler<T> handler) {
        return new AddOperation(pathParameters, value, handler);
    }

    public static RemoveOperation remove(PathParameters pathParameters, ValuelessHandler handler) {
        return new RemoveOperation(pathParameters, handler);
    }

}
