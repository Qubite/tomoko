package io.qubite.tomoko.handler;

import io.qubite.tomoko.handler.value.*;
import io.qubite.tomoko.handler.value.converter.ValueConverterFactory;
import io.qubite.tomoko.handler.valueless.*;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HandlerFactory {

    private final ValueConverterFactory valueConverterFactory;

    HandlerFactory(ValueConverterFactory valueConverterFactory) {
        this.valueConverterFactory = valueConverterFactory;
    }

    public static HandlerFactory instance(ValueConverterFactory valueConverterFactory) {
        return new HandlerFactory(valueConverterFactory);
    }

    public <T> ValueHandler handler(ValueType<T> valueClass, Consumer<T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(consumer);
        return new NullaryValueHandler(consumer, valueConverterFactory.forType(valueClass));
    }

    public <A, T> ValueHandler handler(ValueType<T> valueClass, PathParameter<A> firstParameterNode, BiConsumer<A, T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(consumer);
        return new UnaryValueHandler(consumer, firstParameterNode, valueConverterFactory.forType(valueClass));
    }

    public <A, B, T> ValueHandler handler(ValueType<T> valueClass, PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(consumer);
        return new BinaryValueHandler(consumer, firstParameterNode, secondParameterNode, valueConverterFactory.forType(valueClass));
    }

    public <A, B, C, T> ValueHandler handler(ValueType<T> valueClass, PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, PathParameter<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(thirdParameterNode);
        Preconditions.checkNotNull(consumer);
        return new TernaryValueHandler(consumer, firstParameterNode, secondParameterNode, thirdParameterNode, valueConverterFactory.forType(valueClass));
    }

    public ValuelessHandler handler(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        return new NullaryValuelessHandler(runnable);
    }

    public <A> ValuelessHandler handler(PathParameter<A> firstParameterNode, Consumer<A> consumer) {
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(consumer);
        return new UnaryValuelessHandler(consumer, firstParameterNode);
    }

    public <A, B> ValuelessHandler handler(PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, BiConsumer<A, B> consumer) {
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(consumer);
        return new BinaryValuelessHandler(consumer, firstParameterNode, secondParameterNode);
    }

    public <A, B, C> ValuelessHandler handler(PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, PathParameter<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(thirdParameterNode);
        Preconditions.checkNotNull(consumer);
        return new TernaryValuelessHandler(consumer, firstParameterNode, secondParameterNode, thirdParameterNode);
    }

}
