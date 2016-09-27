package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathParameters;

/**
 * Created by edhendil on 13.08.16.
 */
public class Operations {

    private Operations() {
    }

    public static <T> AddOperation<T> add(PathParameters pathParameters, T value, AddHandler<T> handler) {
        return new AddOperation(pathParameters, value, handler);
    }

    public static RemoveOperation remove(PathParameters pathParameters, RemoveHandler handler) {
        return new RemoveOperation(pathParameters, handler);
    }

}
