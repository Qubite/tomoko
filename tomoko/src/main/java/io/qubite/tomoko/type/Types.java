package io.qubite.tomoko.type;

import io.qubite.tomoko.patch.OperationDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Types {

    private Types() {
    }

    public static <T> SimpleType<T> simple(Class<T> clazz) {
        return new SimpleType(clazz);
    }

    public static SimpleType<String> string() {
        return new SimpleType<>(String.class);
    }

    public static SimpleType<Integer> integer() {
        return new SimpleType<>(Integer.class);
    }

    public static SimpleType<Long> longValue() {
        return new SimpleType<>(Long.class);
    }

    public static SimpleType<Double> doubleValue() {
        return new SimpleType(Double.class);
    }

    public static SimpleType<Float> floatValue() {
        return new SimpleType(Float.class);
    }

    public static SimpleType<Boolean> booleanValue() {
        return new SimpleType(Boolean.class);
    }

    public static <T> GenericType<T> generic(Class<T> base, Class<?>... parameters) {
        return new GenericType(base, parameters);
    }

    public static <C extends Collection<E>, E> CollectionType<Collection<E>, E> collection(Class<C> collection, Class<E> elementClass) {
        return new CollectionType(collection, elementClass);
    }

    public static <L extends List<E>, E> CollectionType<L, E> list(Class<L> list, Class<E> elementClass) {
        return new CollectionType(list, elementClass);
    }

    public static <E> CollectionType<List<E>, E> list(Class<E> elementClass) {
        return new CollectionType(List.class, elementClass);
    }

    public static <M extends Map<K, V>, K, V> MapType<M, K, V> map(Class<M> map, Class<K> keyClass, Class<V> valueClass) {
        return new MapType(map, keyClass, valueClass);
    }

    public static <K, V> MapType<Map<K, V>, K, V> map(Class<K> keyClass, Class<V> valueClass) {
        return new MapType(Map.class, keyClass, valueClass);
    }

    public static CollectionType<List<OperationDto>, OperationDto> operations() {
        return new CollectionType(List.class, OperationDto.class);
    }

}
