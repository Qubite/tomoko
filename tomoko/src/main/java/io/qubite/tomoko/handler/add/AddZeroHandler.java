package io.qubite.tomoko.handler.add;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.type.ValueType;

import java.util.function.Consumer;

/**
 * Created by edhendil on 12.08.16.
 */
public class AddZeroHandler<T> implements AddHandler<T> {

    private final ValueType parameterClass;
    private final Consumer<T> consumer;

    public AddZeroHandler(ValueType parameterClass, Consumer<T> consumer) {
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
