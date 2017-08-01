package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.value.converter.ValueConverter;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.parameter.PathParameter;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

public class ReflectionValueHandler implements ValueHandler {

    private final List<PathParameter<?>> parameters;
    private final ValueConverter<?> valueConverter;
    private final MethodHandle methodHandle;

    ReflectionValueHandler(List<PathParameter<?>> parameters, ValueConverter<?> valueConverter, MethodHandle methodHandle) {
        this.parameters = parameters;
        this.valueConverter = valueConverter;
        this.methodHandle = methodHandle;
    }

    public static ReflectionValueHandler of(List<PathParameter<?>> parameters, ValueConverter<?> valueConverter, MethodHandle methodHandle) {
        return new ReflectionValueHandler(parameters, valueConverter, methodHandle);
    }

    @Override
    public void execute(Path path, ValueTree value) {
        List<Object> parameterValues = new ArrayList<>();
        for (PathParameter<?> parameter : parameters) {
            parameterValues.add(parameter.extractValue(path));
        }
        parameterValues.add(valueConverter.parse(value));
        try {
            methodHandle.invokeExact(parameterValues.toArray());
        } catch (Throwable throwable) {
            throw new PatcherException(throwable);
        }
    }
}
