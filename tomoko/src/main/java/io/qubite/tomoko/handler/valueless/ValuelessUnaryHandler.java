package io.qubite.tomoko.handler.valueless;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;

import java.util.function.Consumer;

/**
 * Created by edhendil on 13.08.16.
 */
public class ValuelessUnaryHandler<A> implements ValuelessHandler {

    private final PathTemplate<A> parameterNode;
    private final Consumer<A> consumer;

    public ValuelessUnaryHandler(PathTemplate<A> parameterNode, Consumer<A> consumer) {
        this.parameterNode = parameterNode;
        this.consumer = consumer;
    }

    @Override
    public void execute(PathParameters pathParameters) {
        consumer.accept(pathParameters.getValue(parameterNode));
    }
}
