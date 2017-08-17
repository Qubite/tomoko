package io.qubite.tomoko.handler.invocation;

import io.qubite.tomoko.util.TriConsumer;

public class TriConsumerInvocation<A, B, C> implements ClientCodeInvocation {

    private final TriConsumer<A, B, C> consumer;

    TriConsumerInvocation(TriConsumer<A, B, C> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(Object[] parameters) {
        if (parameters.length < 3) {
            throw new IllegalArgumentException("Not enough parameters.");
        }
        consumer.accept((A) parameters[0], (B) parameters[1], (C) parameters[2]);
    }

}
