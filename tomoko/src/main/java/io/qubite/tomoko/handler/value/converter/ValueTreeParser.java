package io.qubite.tomoko.handler.value.converter;

import io.qubite.tomoko.type.ValueType;

public interface ValueTreeParser<T> {

    <V> V getAs(T value, ValueType<V> valueType);

    Class<T> supportedType();

}
