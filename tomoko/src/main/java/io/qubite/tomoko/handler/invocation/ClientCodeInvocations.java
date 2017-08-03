package io.qubite.tomoko.handler.invocation;

import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.TriConsumer;

import java.lang.invoke.MethodHandle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class ClientCodeInvocations {

    private ClientCodeInvocations() {
    }

    public static MethodHandleInvocation invocation(MethodHandle methodHandle) {
        return new MethodHandleInvocation(methodHandle);
    }

    public static RunnableInvocation invocation(Runnable runnable) {
        return new RunnableInvocation(runnable);
    }

    public static <A> ConsumerInvocation<A> invocation(Consumer<A> consumer) {
        return new ConsumerInvocation<>(consumer);
    }

    public static <A, B> BiConsumerInvocation<A, B> invocation(BiConsumer<A, B> consumer) {
        return new BiConsumerInvocation<>(consumer);
    }

    public static <A, B, C> TriConsumerInvocation<A, B, C> invocation(TriConsumer<A, B, C> consumer) {
        return new TriConsumerInvocation<>(consumer);
    }

    public static <A, B, C, D> QuadConsumerInvocation<A, B, C, D> invocation(QuadConsumer<A, B, C, D> consumer) {
        return new QuadConsumerInvocation<>(consumer);
    }

}
