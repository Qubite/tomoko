package io.qubite.tomoko.handler;

import io.qubite.tomoko.TomokoException;

public class HandlerExecutionException extends TomokoException {

    private Exception cause;

    public HandlerExecutionException(Exception cause) {
        this.cause = cause;
    }

    @Override
    public Exception getCause() {
        return cause;
    }

}
