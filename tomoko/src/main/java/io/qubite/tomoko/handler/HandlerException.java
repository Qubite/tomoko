package io.qubite.tomoko.handler;

import io.qubite.tomoko.TomokoException;

public class HandlerException extends TomokoException {

    public HandlerException() {
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }

}
