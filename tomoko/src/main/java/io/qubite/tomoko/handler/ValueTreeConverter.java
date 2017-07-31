package io.qubite.tomoko.handler;

import io.qubite.tomoko.handler.value.ValueConverter;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.type.ValueType;

public class ValueTreeConverter<T> implements ValueConverter<T> {

    private final ValueType<T> valueType;

    ValueTreeConverter(ValueType<T> valueType) {
        this.valueType = valueType;
    }

    public static <T> ValueTreeConverter<T> of(ValueType<T> valueType) {
        return new ValueTreeConverter<>(valueType);
    }

    @Override
    public T parse(ValueTree valueTree) {
        return valueTree.getAs(valueType);
    }

}
