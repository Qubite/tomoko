package io.qubite.tomoko.handler.invocation;

import java.util.function.Consumer;

public class ConsumerInvocation<A> implements ClientCodeInvocation {

    private final Consumer<A> consumer;

    ConsumerInvocation(Consumer<A> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(Object[] parameters) {
        if (parameters.length < 1) {
            throw new IllegalArgumentException("Not enough parameters.");
        }
        consumer.accept((A) parameters[0]);
    }

}
