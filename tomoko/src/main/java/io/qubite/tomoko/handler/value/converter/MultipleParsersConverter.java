package io.qubite.tomoko.handler.value.converter;

import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.type.ValueType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultipleParsersConverter<V> implements ValueConverter<V> {

    private final ValueType<V> valueType;
    private final Map<Class<?>, ValueParser<?>> parsers;

    MultipleParsersConverter(ValueType<V> valueType, Map<Class<?>, ValueParser<?>> parsers) {
        this.valueType = valueType;
        this.parsers = parsers;
    }

    public static <T> MultipleParsersConverter<T> of(ValueType<T> valueType, Set<ValueParser<?>> parsers) {
        return new MultipleParsersConverter<>(valueType, toMap(parsers));
    }

    private static Map<Class<?>, ValueParser<?>> toMap(Set<ValueParser<?>> parsers) {
        Map<Class<?>, ValueParser<?>> result = new HashMap<>();
        for (ValueParser<?> parser : parsers) {
            result.put(parser.supportedType(), parser);
        }
        return result;
    }

    @Override
    public V parse(ValueTree valueTree) {
        ValueParser<?> parser = parsers.get(valueTree.getClass());
        if (parser == null) {
            throw new ConverterException("No value converter found for type " + valueTree.getClass().getSimpleName() + ". Check Tomoko configuration.");
        }
        return parse(valueTree, parser);
    }

    private <T> V parse(ValueTree valueTree, ValueParser<T> parser) {
        return parser.getAs((T) valueTree, valueType);
    }

    @Override
    public String toString() {
        return valueType.toString();
    }

}
