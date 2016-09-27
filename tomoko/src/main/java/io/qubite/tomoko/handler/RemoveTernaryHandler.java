package io.qubite.tomoko.handler;

import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;

/**
 * Created by edhendil on 29.08.16.
 */
public class RemoveTernaryHandler<A, B, C> implements RemoveHandler {

    private final PathTemplate<A> firstParameterNode;
    private final PathTemplate<B> secondParameterNode;
    private final PathTemplate<C> thirdParameterNode;
    private final TriConsumer<A, B, C> consumer;

    public RemoveTernaryHandler(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
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
