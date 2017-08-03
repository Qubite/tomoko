package io.qubite.tomoko.handler.invocation;

import io.qubite.tomoko.util.QuadConsumer;

public class QuadConsumerInvocation<A, B, C, D> implements ClientCodeInvocation {

    private final QuadConsumer<A, B, C, D> consumer;

    QuadConsumerInvocation(QuadConsumer<A, B, C, D> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(Object[] parameters) {
        if (parameters.length < 4) {
            throw new IllegalArgumentException("Not enough parameters.");
        }
        consumer.accept((A) parameters[0], (B) parameters[1], (C) parameters[2], (D) parameters[3]);
    }

}
