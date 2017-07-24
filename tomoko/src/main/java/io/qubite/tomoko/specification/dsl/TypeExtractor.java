package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.type.SimpleType;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.TriConsumer;
import net.jodah.typetools.TypeResolver;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TypeExtractor {

    private TypeExtractor() {
    }

    public static <V> SimpleType<V> extractSimpleType(Consumer<V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(Consumer.class, consumer.getClass());
        return toSimpleType((Class<V>) classes[0]);
    }

    public static <A, V> SimpleType<V> extractSimpleType(BiConsumer<A, V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(BiConsumer.class, consumer.getClass());
        return toSimpleType((Class<V>) classes[1]);
    }

    public static <A, B, V> SimpleType<V> extractSimpleType(TriConsumer<A, B, V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(TriConsumer.class, consumer.getClass());
        return toSimpleType((Class<V>) classes[2]);
    }

    public static <A, B, C, V> SimpleType<V> extractSimpleType(QuadConsumer<A, B, C, V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(BiConsumer.class, consumer.getClass());
        return toSimpleType((Class<V>) classes[3]);
    }

    private static <V> SimpleType<V> toSimpleType(Class<V> clazz) {
        Preconditions.checkNotUnknown(clazz);
        Preconditions.checkNonGeneric(clazz);
        return Types.simple(clazz);
    }

}
