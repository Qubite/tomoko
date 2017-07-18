package io.qubite.tomoko;

public class PatcherException extends RuntimeException {

    public PatcherException() {
    }

    public PatcherException(String message) {
        super(message);
    }

    public PatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatcherException(Throwable cause) {
        super(cause);
    }

}
