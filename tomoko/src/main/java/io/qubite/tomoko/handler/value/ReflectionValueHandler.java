package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.handler.HandlerException;
import io.qubite.tomoko.handler.HandlerExecutionException;
import io.qubite.tomoko.handler.value.converter.ValueConverter;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.path.parameter.PathParameter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.WrongMethodTypeException;
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
        Object[] parameterValues = prepareParameters(path, value);
        try {
            methodHandle.invokeExact(parameterValues);
        } catch (WrongMethodTypeException e) {
            throw new IllegalStateException("Problem with Tomoko itself.", e);
        } catch (Throwable throwable) {
            if (throwable instanceof Error) {
                throw (Error) throwable;
            } else if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            } else {
                throw new HandlerExecutionException((Exception) throwable);
            }
        }
    }

    private Object[] prepareParameters(Path path, ValueTree value) {
        List<Object> parameterValues = new ArrayList<>();
        try {
            for (PathParameter<?> parameter : parameters) {
                parameterValues.add(parameter.extractValue(path));
            }
            parameterValues.add(valueConverter.parse(value));
        } catch (ConverterException e) {
            throw new HandlerException(e);
        }
        return parameterValues.toArray();
    }

}
