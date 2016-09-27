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
public class BinaryTypeSpec<A, B> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<?> path;

    BinaryTypeSpec(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<?> path) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.path = path;
    }

    public <V> BinaryHandlerSpec<A, B, V> type(ValueType<V> valueType) {
        return new BinaryHandlerSpec(builder, firstArgumentPath, secondArgumentPath, path, valueType);
    }

    public BinaryHandlerSpec<A, B, String> string() {
        return type(Types.string());
    }

    public BinaryHandlerSpec<A, B, Integer> integer() {
        return type(Types.integer());
    }

    public BinaryHandlerSpec<A, B, Boolean> bool() {
        return type(Types.booleanValue());
    }

    public BinaryHandlerSpec<A, B, Double> real() {
        return type(Types.doubleValue());
    }

    public <T> BinaryHandlerSpec<A, B, T> of(Class<T> valueClass) {
        return type(Types.simple(valueClass));
    }

    public <T> BinaryHandlerSpec<A, B, List<T>> list(Class<T> elementClass) {
        return type(Types.list(elementClass));
    }

    public <L extends List<E>, E> BinaryHandlerSpec<A, B, L> list(Class<L> listClass, Class<E> elementClass) {
        return type(Types.list(listClass, elementClass));
    }

    public <K, V> BinaryHandlerSpec<A, B, Map<K, V>> map(Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(keyClass, valueClass));
    }

    public <M extends Map<K, V>, K, V> BinaryHandlerSpec<A, B, M> map(Class<M> mapClass, Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(mapClass, keyClass, valueClass));
    }

}
