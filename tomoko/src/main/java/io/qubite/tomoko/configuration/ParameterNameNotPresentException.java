package io.qubite.tomoko.configuration;

/**
 * Thrown when a method parameter name was not present but was read anyway.
 */
public class ParameterNameNotPresentException extends RuntimeException {

    public ParameterNameNotPresentException() {
    }

    public ParameterNameNotPresentException(String message) {
        super(message);
    }

    public ParameterNameNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterNameNotPresentException(Throwable cause) {
        super(cause);
    }

}
