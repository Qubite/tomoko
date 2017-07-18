package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.handler.QuadConsumer;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.type.ValueType;

public class ValueTernaryHandler<A, B, C, T> implements ValueHandler<T> {

    private final ValueType parameterClass;
    private final QuadConsumer<A, B, C, T> consumer;
    private final PathTemplate<A> firstParameterNode;
    private final PathTemplate<B> secondParameterNode;
    private final PathTemplate<C> thirdParameterNode;

    public ValueTernaryHandler(ValueType parameterClass, QuadConsumer<A, B, C, T> consumer, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode) {
        this.parameterClass = parameterClass;
        this.consumer = consumer;
        this.firstParameterNode = firstParameterNode;
        this.secondParameterNode = secondParameterNode;
        this.thirdParameterNode = thirdParameterNode;
    }

    @Override
    public ValueType getParameterClass() {
        return parameterClass;
    }

    @Override
    public void execute(PathParameters pathParameters, T parameter) {
        A firstParameter = pathParameters.getValue(firstParameterNode);
        B secondParameter = pathParameters.getValue(secondParameterNode);
        C thirdParameter = pathParameters.getValue(thirdParameterNode);
        consumer.accept(firstParameter, secondParameter, thirdParameter, parameter);
    }

}
