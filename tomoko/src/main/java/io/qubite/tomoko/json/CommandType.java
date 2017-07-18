package io.qubite.tomoko.json;

public enum CommandType {

    ADD("add"), REMOVE("remove"), REPLACE("replace");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public static CommandType of(String name) {
        CommandType[] values = CommandType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getName().equals(name)) {
                return values[i];
            }
        }
        throw new IllegalArgumentException("No CommandType named " + name + " found.");
    }

    public String getName() {
        return name;
    }

}
