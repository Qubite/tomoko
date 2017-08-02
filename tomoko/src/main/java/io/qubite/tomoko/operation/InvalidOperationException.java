package io.qubite.tomoko.operation;

import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.patch.OperationDto;

public class InvalidOperationException extends TomokoException {

    private OperationDto operation;

    public InvalidOperationException(OperationDto operation) {
        this.operation = operation;
    }

    public InvalidOperationException(OperationDto operation, Throwable cause) {
        super(cause);
        this.operation = operation;
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }

    public OperationDto getOperation() {
        return operation;
    }

}
