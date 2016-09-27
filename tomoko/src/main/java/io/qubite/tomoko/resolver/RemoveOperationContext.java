package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathParameters;

/**
 * Created by edhendil on 12.08.16.
 */
public class RemoveOperationContext {

    private final PathParameters pathParameters;
    private final RemoveHandler handler;

    RemoveOperationContext(PathParameters pathParameters, RemoveHandler handler) {
        this.pathParameters = pathParameters;
        this.handler = handler;
    }

    public static RemoveOperationContext of(PathParameters pathParameters, RemoveHandler handler) {
        return new RemoveOperationContext(pathParameters, handler);
    }

    public PathParameters getPathParameters() {
        return pathParameters;
    }

    public RemoveHandler getHandler() {
        return handler;
    }

}
