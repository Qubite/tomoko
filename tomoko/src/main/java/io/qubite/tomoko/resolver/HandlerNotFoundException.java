package io.qubite.tomoko.resolver;

import io.qubite.tomoko.TomokoException;

public class HandlerNotFoundException extends TomokoException {

    public HandlerNotFoundException() {
    }

    public HandlerNotFoundException(String message) {
        super(message);
    }

    public HandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerNotFoundException(Throwable cause) {
        super(cause);
    }

}
