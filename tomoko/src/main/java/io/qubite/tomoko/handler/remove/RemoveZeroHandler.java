package io.qubite.tomoko.handler.remove;

import io.qubite.tomoko.path.PathParameters;

/**
 * Created by edhendil on 13.08.16.
 */
public class RemoveZeroHandler implements RemoveHandler {

    private final Runnable runnable;

    public RemoveZeroHandler(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void execute(PathParameters pathParameters) {
        runnable.run();
    }

}
