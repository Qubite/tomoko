package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.handler.HandlerException;
import io.qubite.tomoko.handler.HandlerExecutionException;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.path.parameter.PathParameter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.WrongMethodTypeException;
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
        Object[] parameters = prepareParameters(path);
        try {
            methodHandle.invokeExact(parameters);
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

    private Object[] prepareParameters(Path path) {
        List<Object> parameterValues = new ArrayList<>();
        for (PathParameter<?> parameter : parameters) {
            try {
                parameterValues.add(parameter.extractValue(path));
            } catch (ConverterException e) {
                throw new HandlerException(e);
            }
        }
        return parameterValues.toArray();
    }

}
