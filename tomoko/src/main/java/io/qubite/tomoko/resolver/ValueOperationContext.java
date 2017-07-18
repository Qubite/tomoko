package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.path.PathParameters;

public class ValueOperationContext<T> {

    private final PathParameters pathParameters;
    private final ValueHandler<T> handler;
    private final JsonTree value;

    ValueOperationContext(PathParameters pathParameters, ValueHandler<T> handler, JsonTree value) {
        this.pathParameters = pathParameters;
        this.handler = handler;
        this.value = value;
    }

    public static <T> ValueOperationContext of(PathParameters pathParameters, ValueHandler<T> handler, JsonTree value) {
        return new ValueOperationContext(pathParameters, handler, value);
    }

    public PathParameters getPathParameters() {
        return pathParameters;
    }

    public ValueHandler<T> getHandler() {
        return handler;
    }

    public JsonTree getValue() {
        return value;
    }

}
