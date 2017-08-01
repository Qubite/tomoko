package io.qubite.tomoko.handler.value.converter;

import io.qubite.tomoko.type.ValueType;

public interface ValueConverterFactory {

    <T> ValueConverter<T> forType(ValueType<T> type);

}
