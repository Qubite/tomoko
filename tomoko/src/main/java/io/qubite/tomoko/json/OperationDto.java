package io.qubite.tomoko.json;

public class OperationDto {

    private final String path;
    private final CommandType type;
    private final JsonTree value;

    OperationDto(String path, CommandType type, JsonTree value) {
        this.path = path;
        this.type = type;
        this.value = value;
    }

    public static OperationDto of(String path, CommandType type, JsonTree value) {
        return new OperationDto(path, type, value);
    }

    public static OperationDto add(String path, JsonTree value) {
        return new OperationDto(path, CommandType.ADD, value);
    }

    public static OperationDto remove(String path) {
        return new OperationDto(path, CommandType.REMOVE, null);
    }

    public String getPath() {
        return path;
    }

    public CommandType getType() {
        return type;
    }

    public JsonTree getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("OperationDto[path: \"%s\", type: %s, value: %s]", path, type, value);
    }
}
