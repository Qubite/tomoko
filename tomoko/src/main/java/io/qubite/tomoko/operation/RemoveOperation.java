package io.qubite.tomoko.operation;

import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathParameters;

/**
 * Created by edhendil on 13.08.16.
 */
public class RemoveOperation implements Operation {

    private final PathParameters pathParameters;
    private final ValuelessHandler handler;

    public RemoveOperation(PathParameters pathParameters, ValuelessHandler handler) {
        this.pathParameters = pathParameters;
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.execute(pathParameters);
    }

}
