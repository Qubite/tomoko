package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.type.ValueType;

public class ValueBinaryHandler<A, B, T> implements ValueHandler<T> {

    private final ValueType parameterClass;
    private final TriConsumer<A, B, T> consumer;
    private final PathTemplate<A> firstParameterNode;
    private final PathTemplate<B> secondParameterNode;

    public ValueBinaryHandler(ValueType parameterClass, TriConsumer<A, B, T> consumer, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode) {
        this.parameterClass = parameterClass;
        this.consumer = consumer;
        this.firstParameterNode = firstParameterNode;
        this.secondParameterNode = secondParameterNode;
    }

    @Override
    public ValueType getParameterClass() {
        return parameterClass;
    }

    @Override
    public void execute(PathParameters pathParameters, T parameter) {
        A firstParameter = pathParameters.getValue(firstParameterNode);
        B secondParameter = pathParameters.getValue(secondParameterNode);
        consumer.accept(firstParameter, secondParameter, parameter);
    }
}
