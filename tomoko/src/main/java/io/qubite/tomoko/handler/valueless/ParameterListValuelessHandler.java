package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.handler.HandlerException;
import io.qubite.tomoko.handler.invocation.ClientCodeInvocation;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.path.parameter.PathParameter;

import java.util.ArrayList;
import java.util.List;

public class ParameterListValuelessHandler implements ValuelessHandler {

    private final List<PathParameter<?>> parameters;
    private final ClientCodeInvocation methodHandle;

    ParameterListValuelessHandler(List<PathParameter<?>> parameters, ClientCodeInvocation methodHandle) {
        this.parameters = parameters;
        this.methodHandle = methodHandle;
    }

    public static ParameterListValuelessHandler of(List<PathParameter<?>> parameters, ClientCodeInvocation methodHandle) {
        return new ParameterListValuelessHandler(parameters, methodHandle);
    }

    @Override
    public void execute(Path path) {
        Object[] parameters = prepareParameters(path);
        methodHandle.invoke(parameters);
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
