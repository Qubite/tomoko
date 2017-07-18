package io.qubite.tomoko.handler;

import io.qubite.tomoko.handler.value.*;
import io.qubite.tomoko.handler.valueless.*;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.type.ValueType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Handlers {

    private Handlers() {
    }

    public static <T> ValueHandler handler(ValueType valueClass, Consumer<T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(consumer);
        return new ValueNullaryHandler(valueClass, consumer);
    }

    public static <A, T> ValueHandler handler(ValueType valueClass, PathTemplate<A> firstParameterNode, BiConsumer<A, T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(firstParameterNode);
        checkNotNull(consumer);
        return new ValueUnaryHandler(valueClass, consumer, firstParameterNode);
    }

    public static <A, B, T> ValueHandler handler(ValueType valueClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(consumer);
        return new ValueBinaryHandler(valueClass, consumer, firstParameterNode, secondParameterNode);
    }

    public static <A, B, C, T> ValueHandler handler(ValueType valueClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(thirdParameterNode);
        checkNotNull(consumer);
        return new ValueTernaryHandler(valueClass, consumer, firstParameterNode, secondParameterNode, thirdParameterNode);
    }

    public static ValuelessHandler handler(Runnable runnable) {
        checkNotNull(runnable);
        return new ValuelessNullaryHandler(runnable);
    }

    public static <A> ValuelessHandler handler(PathTemplate<A> firstParameterNode, Consumer<A> consumer) {
        checkNotNull(firstParameterNode);
        checkNotNull(consumer);
        return new ValuelessUnaryHandler(firstParameterNode, consumer);
    }

    public static <A, B> ValuelessHandler handler(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, BiConsumer<A, B> consumer) {
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(consumer);
        return new ValuelessBinaryHandler(firstParameterNode, secondParameterNode, consumer);
    }

    public static <A, B, C> ValuelessHandler handler(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(thirdParameterNode);
        checkNotNull(consumer);
        return new ValuelessTernaryHandler(firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
    }

    private static void checkNotNull(Object toCheck) {
        if (toCheck == null) {
            throw new NullPointerException();
        }
    }

}
