package io.qubite.tomoko.tree;

import io.qubite.tomoko.TomokoException;

public class IllegalPathExtensionException extends TomokoException {

    public IllegalPathExtensionException() {
    }

    public IllegalPathExtensionException(String message) {
        super(message);
    }

    public IllegalPathExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPathExtensionException(Throwable cause) {
        super(cause);
    }

}
