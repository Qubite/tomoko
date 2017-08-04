package io.qubite.tomoko.patch;

import io.qubite.tomoko.TomokoException;

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
