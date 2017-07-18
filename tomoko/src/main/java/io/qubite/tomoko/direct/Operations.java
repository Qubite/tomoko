package io.qubite.tomoko.direct;

import io.qubite.tomoko.json.CommandType;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.path.Path;

/**
 * Created by edhendil on 19.08.16.
 */
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

}
