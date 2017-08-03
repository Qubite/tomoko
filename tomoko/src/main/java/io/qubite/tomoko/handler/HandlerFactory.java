package io.qubite.tomoko.handler;

import io.qubite.tomoko.handler.invocation.ClientCodeInvocation;
import io.qubite.tomoko.handler.invocation.ClientCodeInvocations;
import io.qubite.tomoko.handler.value.ParameterListValueHandler;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.value.converter.ValueConverter;
import io.qubite.tomoko.handler.value.converter.ValueConverterFactory;
import io.qubite.tomoko.handler.valueless.ParameterListValuelessHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.TriConsumer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        return handler(Collections.emptyList(), valueConverterFactory.forType(valueClass), ClientCodeInvocations.invocation(consumer));
    }

    public <A, T> ValueHandler handler(ValueType<T> valueClass, PathParameter<A> firstParameterNode, BiConsumer<A, T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(consumer);
        return handler(Arrays.asList(firstParameterNode), valueConverterFactory.forType(valueClass), ClientCodeInvocations.invocation(consumer));
    }

    public <A, B, T> ValueHandler handler(ValueType<T> valueClass, PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(consumer);
        return handler(Arrays.asList(firstParameterNode, secondParameterNode), valueConverterFactory.forType(valueClass), ClientCodeInvocations.invocation(consumer));
    }

    public <A, B, C, T> ValueHandler handler(ValueType<T> valueClass, PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, PathParameter<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        Preconditions.checkNotNull(valueClass);
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(thirdParameterNode);
        Preconditions.checkNotNull(consumer);
        return handler(Arrays.asList(firstParameterNode, secondParameterNode, thirdParameterNode), valueConverterFactory.forType(valueClass), ClientCodeInvocations.invocation(consumer));
    }

    private ValueHandler handler(List<PathParameter<?>> parameters, ValueConverter<?> converter, ClientCodeInvocation clientCodeInvocation) {
        return ParameterListValueHandler.of(parameters, converter, clientCodeInvocation);
    }

    public ValuelessHandler handler(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        return handler(Collections.emptyList(), ClientCodeInvocations.invocation(runnable));
    }

    public <A> ValuelessHandler handler(PathParameter<A> firstParameterNode, Consumer<A> consumer) {
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(consumer);
        return handler(Arrays.asList(firstParameterNode), ClientCodeInvocations.invocation(consumer));
    }

    public <A, B> ValuelessHandler handler(PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, BiConsumer<A, B> consumer) {
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(consumer);
        return handler(Arrays.asList(firstParameterNode, secondParameterNode), ClientCodeInvocations.invocation(consumer));
    }

    public <A, B, C> ValuelessHandler handler(PathParameter<A> firstParameterNode, PathParameter<B> secondParameterNode, PathParameter<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        Preconditions.checkNotNull(firstParameterNode);
        Preconditions.checkNotNull(secondParameterNode);
        Preconditions.checkNotNull(thirdParameterNode);
        Preconditions.checkNotNull(consumer);
        return handler(Arrays.asList(firstParameterNode, secondParameterNode, thirdParameterNode), ClientCodeInvocations.invocation(consumer));
    }

    private ValuelessHandler handler(List<PathParameter<?>> parameters, ClientCodeInvocation clientCodeInvocation) {
        return ParameterListValuelessHandler.of(parameters, clientCodeInvocation);
    }

}
