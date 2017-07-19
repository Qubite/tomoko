package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathParameters;

public class Operations {

    private Operations() {
    }

    public static <T> ValueOperation<T> value(PathParameters pathParameters, T value, ValueHandler<T> handler) {
        return new ValueOperation(pathParameters, value, handler);
    }

    public static ValuelessOperation valueless(PathParameters pathParameters, ValuelessHandler handler) {
        return new ValuelessOperation(pathParameters, handler);
    }

}
