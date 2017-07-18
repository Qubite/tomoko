package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;

public class ValuelessTernaryHandler<A, B, C> implements ValuelessHandler {

    private final PathTemplate<A> firstParameterNode;
    private final PathTemplate<B> secondParameterNode;
    private final PathTemplate<C> thirdParameterNode;
    private final TriConsumer<A, B, C> consumer;

    public ValuelessTernaryHandler(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        this.firstParameterNode = firstParameterNode;
        this.secondParameterNode = secondParameterNode;
        this.thirdParameterNode = thirdParameterNode;
        this.consumer = consumer;
    }

    @Override
    public void execute(PathParameters pathParameters) {
        consumer.accept(pathParameters.getValue(firstParameterNode), pathParameters.getValue(secondParameterNode), pathParameters.getValue(thirdParameterNode));
    }

}
