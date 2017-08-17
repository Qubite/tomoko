package io.qubite.tomoko.patch;

import io.qubite.tomoko.TomokoException;

/**
 * Exception thrown when the request body cannot be parsed as a valid patch.
 */
public class PatchParseException extends TomokoException {

    public PatchParseException() {
    }

    public PatchParseException(String message) {
        super(message);
    }

    public PatchParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatchParseException(Throwable cause) {
        super(cause);
    }

}
