package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.path.PathParameters;

/**
 * Created by edhendil on 17.08.16.
 */
public class AddOperationContext<T> {

    private final PathParameters pathParameters;
    private final AddHandler<T> handler;
    private final JsonTree value;

    AddOperationContext(PathParameters pathParameters, AddHandler<T> handler, JsonTree value) {
        this.pathParameters = pathParameters;
        this.handler = handler;
        this.value = value;
    }

    public static <T> AddOperationContext of(PathParameters pathParameters, AddHandler<T> handler, JsonTree value) {
        return new AddOperationContext(pathParameters, handler, value);
    }

    public PathParameters getPathParameters() {
        return pathParameters;
    }

    public AddHandler<T> getHandler() {
        return handler;
    }

    public JsonTree getValue() {
        return value;
    }

}
