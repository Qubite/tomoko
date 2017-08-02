package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.TomokoException;

public class ConverterException extends TomokoException {

    public ConverterException() {
    }

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConverterException(Throwable cause) {
        super(cause);
    }

}
