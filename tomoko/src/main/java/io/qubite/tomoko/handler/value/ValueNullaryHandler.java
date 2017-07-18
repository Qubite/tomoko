package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.type.ValueType;

import java.util.function.Consumer;

/**
 * Created by edhendil on 12.08.16.
 */
public class ValueNullaryHandler<T> implements ValueHandler<T> {

    private final ValueType parameterClass;
    private final Consumer<T> consumer;

    public ValueNullaryHandler(ValueType parameterClass, Consumer<T> consumer) {
        this.parameterClass = parameterClass;
        this.consumer = consumer;
    }

    @Override
    public ValueType getParameterClass() {
        return parameterClass;
    }

    @Override
    public void execute(PathParameters pathParameters, T parameter) {
        consumer.accept(parameter);
    }

}
