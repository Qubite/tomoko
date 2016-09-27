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
public class TernaryTypeSpec<A, B, C> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<C> thirdArgumentPath;
    private final PathTemplate<?> path;

    TernaryTypeSpec(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<C> thirdArgumentPath, PathTemplate<?> path) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.thirdArgumentPath = thirdArgumentPath;
        this.path = path;
    }

    public <V> TernaryHandlerSpec<A, B, C, V> type(ValueType<V> valueType) {
        return new TernaryHandlerSpec(builder, firstArgumentPath, secondArgumentPath, thirdArgumentPath, path, valueType);
    }

    public TernaryHandlerSpec<A, B, C, String> string() {
        return type(Types.string());
    }

    public TernaryHandlerSpec<A, B, C, Integer> integer() {
        return type(Types.integer());
    }

    public TernaryHandlerSpec<A, B, C, Boolean> bool() {
        return type(Types.booleanValue());
    }

    public TernaryHandlerSpec<A, B, C, Double> real() {
        return type(Types.doubleValue());
    }

    public <T> TernaryHandlerSpec<A, B, C, T> of(Class<T> valueClass) {
        return type(Types.simple(valueClass));
    }

    public <T> TernaryHandlerSpec<A, B, C, List<T>> list(Class<T> elementClass) {
        return type(Types.list(elementClass));
    }

    public <L extends List<E>, E> TernaryHandlerSpec<A, B, C, L> list(Class<L> listClass, Class<E> elementClass) {
        return type(Types.list(listClass, elementClass));
    }

    public <K, V> TernaryHandlerSpec<A, B, C, Map<K, V>> map(Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(keyClass, valueClass));
    }

    public <M extends Map<K, V>, K, V> TernaryHandlerSpec<A, B, C, M> map(Class<M> mapClass, Class<K> keyClass, Class<V> valueClass) {
        return type(Types.map(mapClass, keyClass, valueClass));
    }

}
