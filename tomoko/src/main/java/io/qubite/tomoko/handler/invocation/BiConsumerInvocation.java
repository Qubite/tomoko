package io.qubite.tomoko.handler.invocation;

import java.util.function.BiConsumer;

public class BiConsumerInvocation<A, B> implements ClientCodeInvocation {

    private final BiConsumer<A, B> consumer;

    BiConsumerInvocation(BiConsumer<A, B> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(Object[] parameters) {
        if (parameters.length < 2) {
            throw new IllegalArgumentException("Not enough parameters.");
        }
        consumer.accept((A) parameters[0], (B) parameters[1]);
    }

}
