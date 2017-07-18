package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathParameters;

public class ValuelessOperationContext {

    private final PathParameters pathParameters;
    private final ValuelessHandler handler;

    ValuelessOperationContext(PathParameters pathParameters, ValuelessHandler handler) {
        this.pathParameters = pathParameters;
        this.handler = handler;
    }

    public static ValuelessOperationContext of(PathParameters pathParameters, ValuelessHandler handler) {
        return new ValuelessOperationContext(pathParameters, handler);
    }

    public PathParameters getPathParameters() {
        return pathParameters;
    }

    public ValuelessHandler getHandler() {
        return handler;
    }

}
