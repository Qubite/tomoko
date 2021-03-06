package io.qubite.tomoko.direct;

import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.path.Path;

public class Operations {

    private Operations() {
    }

    public static OperationDto add(String path, DirectTree valueTree) {
        return OperationDto.of(path, CommandType.ADD, valueTree);
    }

    public static OperationDto add(Path path, DirectTree valueTree) {
        return OperationDto.of(path.toString(), CommandType.ADD, valueTree);
    }

    public static OperationDto remove(Path path) {
        return OperationDto.of(path.toString(), CommandType.REMOVE, null);
    }

    public static OperationDto remove(String path) {
        return OperationDto.of(path, CommandType.REMOVE, null);
    }

    public static OperationDto replace(String path, DirectTree valueTree) {
        return OperationDto.of(path, CommandType.REPLACE, valueTree);
    }

    public static OperationDto replace(Path path, DirectTree valueTree) {
        return OperationDto.of(path.toString(), CommandType.REPLACE, valueTree);
    }

    public static OperationDto value(CommandType type, String path, DirectTree valueTree) {
        checkValueType(type);
        return OperationDto.of(path, type, valueTree);
    }

    public static OperationDto value(CommandType type, Path path, DirectTree valueTree) {
        checkValueType(type);
        return OperationDto.of(path.toString(), type, valueTree);
    }

    private static void checkValueType(CommandType type) {
        if (type.isValueless()) {
            throw new IllegalArgumentException("Command type must not be valueless");
        }
    }

}
