package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.path.PathParameters;

public class ValuelessNullaryHandler implements ValuelessHandler {

    private final Runnable runnable;

    public ValuelessNullaryHandler(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void execute(PathParameters pathParameters) {
        runnable.run();
    }

}
