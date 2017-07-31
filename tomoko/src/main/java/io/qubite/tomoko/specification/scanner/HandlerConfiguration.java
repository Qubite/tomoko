package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.patch.CommandType;

import java.lang.reflect.Method;

public class HandlerConfiguration {

    private final Method method;
    private final String path;
    private final CommandType commandType;

    HandlerConfiguration(Method method, String path, CommandType commandType) {
        this.method = method;
        this.path = path;
        this.commandType = commandType;
    }

    public static HandlerConfiguration of(Method method, String path, CommandType commandType) {
        return new HandlerConfiguration(method, path, commandType);
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public CommandType getCommandType() {
        return commandType;
    }

}
