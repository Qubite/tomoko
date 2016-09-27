package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;

import java.util.List;
import java.util.Map;

/**
 * Created by edhendil on 29.08.16.
 */
public class UnaryTypeSpec<A> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<?> path;

    UnaryTypeSpec(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<?> path) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.path = path;
    }

    public <V> UnaryHandlerSpec<A, V> type(ValueType<V> valueType) {
        return new UnaryHandlerSpec(builder, firstArgumentPath, path, valueType);
    }

    public UnaryHandlerSpec<A, String> string() {
        return type(Types.string());
    }

    public UnaryHandlerSpec<A, Integer> integer() {
        return type(Types.integer());
    }

    public UnaryHandlerSpec<A, Boolean> bool() {
        return type(Types.booleanValue());
    }

    public UnaryHandlerSpec<A, Double> real() {
        return type(Types.doubleValue());
    }

    public <T> UnaryHandlerSpec<A, T> of(Class<T> valueClass) {
        return type(Types.simple(valueClass));
    }

    public <T> UnaryHandlerSpec<A, List<T>> list(Class<T> elementClass) {
        return type(Types.list(elementClass));
    }

    public <L extends List<E>, E> UnaryHandlerSpec<A, L> list(Class<L> listClass, Class<E> elementClass) {
        return type(Types.list(listClass, elementClass));
    }

    public <K, V> UnaryHandlerSpec<A, Map<K, V>> map(Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(keyClass, valueClass));
    }

    public <M extends Map<K, V>, K, V> UnaryHandlerSpec<A, M> map(Class<M> mapClass, Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(mapClass, keyClass, valueClass));
    }

}
