package io.qubite.tomoko.handler.add;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.type.ValueType;

import java.util.function.BiConsumer;

/**
 * Created by edhendil on 11.08.16.
 */
public class AddUnaryHandler<A, T> implements AddHandler<T> {

    private final ValueType parameterClass;
    private final BiConsumer<A, T> consumer;
    private final PathTemplate<A> parameterNode;

    public AddUnaryHandler(ValueType parameterClass, BiConsumer<A, T> consumer, PathTemplate<A> parameterNode) {
        this.parameterClass = parameterClass;
        this.consumer = consumer;
        this.parameterNode = parameterNode;
    }

    @Override
    public ValueType getParameterClass() {
        return parameterClass;
    }

    @Override
    public void execute(PathParameters pathParameters, T parameter) {
        A param = pathParameters.getValue(parameterNode);
        consumer.accept(param, parameter);
    }

}
