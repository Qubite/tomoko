package io.qubite.tomoko.patch;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Set of operations packed together.
 */
public class Patch {

    private final List<OperationDto> operations;

    Patch(List<OperationDto> operations) {
        this.operations = operations;
    }

    public static Patch of(List<OperationDto> operations) {
        return new Patch(operations);
    }

    public List<OperationDto> getOperations() {
        return Collections.unmodifiableList(operations);
    }

    public Patch prepend(String path) {
        return new Patch(operations.stream().map((operation) -> prepend(operation, path)).collect(Collectors.toList()));
    }

    private OperationDto prepend(OperationDto operation, String path) {
        return OperationDto.of(path + operation.getPath(), operation.getType(), operation.getValue());
    }

}
