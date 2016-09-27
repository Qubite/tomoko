package io.qubite.tomoko.handler.remove;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;

import java.util.function.BiConsumer;

/**
 * Created by edhendil on 13.08.16.
 */
public class RemoveBinaryHandler<A, B> implements RemoveHandler {

    private final PathTemplate<A> firstParameterNode;
    private final PathTemplate<B> secondParameterNode;
    private final BiConsumer<A, B> consumer;

    public RemoveBinaryHandler(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, BiConsumer<A, B> consumer) {
        this.firstParameterNode = firstParameterNode;
        this.secondParameterNode = secondParameterNode;
        this.consumer = consumer;
    }

    @Override
    public void execute(PathParameters pathParameters) {
        consumer.accept(pathParameters.getValue(firstParameterNode), pathParameters.getValue(secondParameterNode));
    }

}
