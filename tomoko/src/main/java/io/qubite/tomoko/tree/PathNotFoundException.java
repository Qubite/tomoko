package io.qubite.tomoko.tree;

import io.qubite.tomoko.TomokoException;

public class PathNotFoundException extends TomokoException {

    public PathNotFoundException() {
    }

    public PathNotFoundException(String message) {
        super(message);
    }

    public PathNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathNotFoundException(Throwable cause) {
        super(cause);
    }

}
