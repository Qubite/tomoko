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
public class NullaryTypeSpec {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<?> path;

    NullaryTypeSpec(TreeSpecificationBuilder builder, PathTemplate<?> path) {
        this.builder = builder;
        this.path = path;
    }

    public <V> NullaryHandlerSpec<V> type(ValueType<V> valueType) {
        return new NullaryHandlerSpec(builder, path, valueType);
    }

    public NullaryHandlerSpec<String> string() {
        return type(Types.string());
    }

    public NullaryHandlerSpec<Integer> integer() {
        return type(Types.integer());
    }

    public NullaryHandlerSpec<Boolean> bool() {
        return type(Types.booleanValue());
    }

    public NullaryHandlerSpec<Double> real() {
        return type(Types.doubleValue());
    }

    public <T> NullaryHandlerSpec<T> of(Class<T> valueClass) {
        return type(Types.simple(valueClass));
    }

    public <T> NullaryHandlerSpec<List<T>> list(Class<T> elementClass) {
        return type(Types.list(elementClass));
    }

    public <L extends List<E>, E> NullaryHandlerSpec<L> list(Class<L> listClass, Class<E> elementClass) {
        return type(Types.list(listClass, elementClass));
    }

    public <K, V> NullaryHandlerSpec<Map<K, V>> map(Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(keyClass, valueClass));
    }

    public <M extends Map<K, V>, K, V> NullaryHandlerSpec<M> map(Class<M> mapClass, Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(mapClass, keyClass, valueClass));
    }

}
