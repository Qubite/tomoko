package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.handler.HandlerException;
import io.qubite.tomoko.handler.invocation.ClientCodeInvocation;
import io.qubite.tomoko.handler.value.converter.ValueConverter;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.path.parameter.PathParameter;

import java.util.ArrayList;
import java.util.List;

public class ParameterListValueHandler implements ValueHandler {

    private final List<PathParameter<?>> parameters;
    private final ValueConverter<?> valueConverter;
    private final ClientCodeInvocation methodHandle;

    ParameterListValueHandler(List<PathParameter<?>> parameters, ValueConverter<?> valueConverter, ClientCodeInvocation methodHandle) {
        this.parameters = parameters;
        this.valueConverter = valueConverter;
        this.methodHandle = methodHandle;
    }

    public static ParameterListValueHandler of(List<PathParameter<?>> parameters, ValueConverter<?> valueConverter, ClientCodeInvocation methodHandle) {
        return new ParameterListValueHandler(parameters, valueConverter, methodHandle);
    }

    @Override
    public void execute(Path path, ValueTree value) {
        Object[] parameterValues = prepareParameters(path, value);
        methodHandle.invoke(parameterValues);
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
