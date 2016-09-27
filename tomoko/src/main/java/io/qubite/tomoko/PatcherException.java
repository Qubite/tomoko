package io.qubite.tomoko;

/**
 * Created by edhendil on 16.08.16.
 */
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
