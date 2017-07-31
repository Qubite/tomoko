package io.qubite.tomoko.type;

import java.util.Map;

public class MapType<M extends Map<K, V>, K, V> implements ValueType<M> {

    private final Class<M> baseClass;
    private final Class<K> keyClass;
    private final Class<V> valueClass;

    public MapType(Class<M> baseClass, Class<K> keyClass, Class<V> valueClass) {
        this.baseClass = baseClass;
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    public Class<M> getBaseClass() {
        return baseClass;
    }

    public Class<K> getKeyClass() {
        return keyClass;
    }

    public Class<V> getValueClass() {
        return valueClass;
    }

    @Override
    public String toString() {
        return "Map<" + keyClass.getSimpleName() + ", " + valueClass.getSimpleName() + ">";
    }
}
