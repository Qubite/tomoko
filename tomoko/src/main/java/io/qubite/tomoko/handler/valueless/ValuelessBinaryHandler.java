package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;

import java.util.function.BiConsumer;

public class ValuelessBinaryHandler<A, B> implements ValuelessHandler {

    private final PathTemplate<A> firstParameterNode;
    private final PathTemplate<B> secondParameterNode;
    private final BiConsumer<A, B> consumer;

    public ValuelessBinaryHandler(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, BiConsumer<A, B> consumer) {
        this.firstParameterNode = firstParameterNode;
        this.secondParameterNode = secondParameterNode;
        this.consumer = consumer;
    }

    @Override
    public void execute(PathParameters pathParameters) {
        consumer.accept(pathParameters.getValue(firstParameterNode), pathParameters.getValue(secondParameterNode));
    }

}
