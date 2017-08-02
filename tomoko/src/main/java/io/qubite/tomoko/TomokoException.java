package io.qubite.tomoko;

public class TomokoException extends RuntimeException {

    public TomokoException() {
    }

    public TomokoException(String message) {
        super(message);
    }

    public TomokoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomokoException(Throwable cause) {
        super(cause);
    }

}
