package io.qubite.tomoko.handler;

import io.qubite.tomoko.handler.add.*;
import io.qubite.tomoko.handler.remove.RemoveBinaryHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.handler.remove.RemoveUnaryHandler;
import io.qubite.tomoko.handler.remove.RemoveZeroHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.type.ValueType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by edhendil on 12.08.16.
 */
public class Handlers {

    private Handlers() {
    }

    public static <T> AddHandler add(ValueType valueClass, Consumer<T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(consumer);
        return new AddZeroHandler(valueClass, consumer);
    }

    public static <A, T> AddHandler add(ValueType valueClass, PathTemplate<A> firstParameterNode, BiConsumer<A, T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(firstParameterNode);
        checkNotNull(consumer);
        return new AddUnaryHandler(valueClass, consumer, firstParameterNode);
    }

    public static <A, B, T> AddHandler add(ValueType valueClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(consumer);
        return new AddBinaryHandler(valueClass, consumer, firstParameterNode, secondParameterNode);
    }

    public static <A, B, C, T> AddHandler add(ValueType valueClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        checkNotNull(valueClass);
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(thirdParameterNode);
        checkNotNull(consumer);
        return new AddTernaryHandler(valueClass, consumer, firstParameterNode, secondParameterNode, thirdParameterNode);
    }

    public static RemoveHandler remove(Runnable runnable) {
        checkNotNull(runnable);
        return new RemoveZeroHandler(runnable);
    }

    public static <A> RemoveHandler remove(PathTemplate<A> firstParameterNode, Consumer<A> consumer) {
        checkNotNull(firstParameterNode);
        checkNotNull(consumer);
        return new RemoveUnaryHandler(firstParameterNode, consumer);
    }

    public static <A, B> RemoveHandler remove(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, BiConsumer<A, B> consumer) {
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(consumer);
        return new RemoveBinaryHandler(firstParameterNode, secondParameterNode, consumer);
    }

    public static <A, B, C> RemoveHandler remove(PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        checkNotNull(firstParameterNode);
        checkNotNull(secondParameterNode);
        checkNotNull(thirdParameterNode);
        checkNotNull(consumer);
        return new RemoveTernaryHandler(firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
    }

    private static void checkNotNull(Object toCheck) {
        if (toCheck == null) {
            throw new NullPointerException();
        }
    }

}
