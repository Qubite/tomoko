package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathParameter;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

public class ReflectionValuelessHandler implements ValuelessHandler {

    private final List<PathParameter<?>> parameters;
    private final MethodHandle methodHandle;

    ReflectionValuelessHandler(List<PathParameter<?>> parameters, MethodHandle methodHandle) {
        this.parameters = parameters;
        this.methodHandle = methodHandle;
    }

    public static ReflectionValuelessHandler of(List<PathParameter<?>> parameters, MethodHandle methodHandle) {
        return new ReflectionValuelessHandler(parameters, methodHandle);
    }

    @Override
    public void execute(Path path) {
        List<Object> parameterValues = new ArrayList<>();
        for (PathParameter<?> parameter : parameters) {
            parameterValues.add(parameter.extractValue(path));
        }
        try {
            methodHandle.invokeExact(parameterValues.toArray());
        } catch (Throwable throwable) {
            throw new PatcherException(throwable);
        }
    }
}
