package io.qubite.tomoko.json;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by edhendil on 03.09.16.
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

    public Patch appendPath(String path) {
        return new Patch(operations.stream().map((operation) -> append(operation, path)).collect(Collectors.toList()));
    }

    private OperationDto append(OperationDto operation, String path) {
        return OperationDto.of(path + operation.getPath(), operation.getType(), operation.getValue());
    }

}
