package io.qubite.tomoko.configuration;

import io.qubite.tomoko.TomokoException;

/**
 * Indicates there was a problem when creating a patcher due to a bad configuration ie. not properly annotated method.
 */
public class ConfigurationException extends TomokoException {

    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

}
