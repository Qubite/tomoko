package io.qubite.tomoko.handler.value.converter;

import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.type.ValueType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultipleParsersConverter<V> implements ValueConverter<V> {

    private final ValueType<V> valueType;
    private final Map<Class<?>, ValueTreeParser<?>> parsers;

    MultipleParsersConverter(ValueType<V> valueType, Map<Class<?>, ValueTreeParser<?>> parsers) {
        this.valueType = valueType;
        this.parsers = parsers;
    }

    public static <T> MultipleParsersConverter<T> of(ValueType<T> valueType, Set<ValueTreeParser<?>> parsers) {
        return new MultipleParsersConverter<>(valueType, toMap(parsers));
    }

    private static Map<Class<?>, ValueTreeParser<?>> toMap(Set<ValueTreeParser<?>> parsers) {
        Map<Class<?>, ValueTreeParser<?>> result = new HashMap<>();
        for (ValueTreeParser<?> parser : parsers) {
            result.put(parser.supportedType(), parser);
        }
        return result;
    }

    @Override
    public V parse(ValueTree valueTree) {
        ValueTreeParser<?> parser = parsers.get(valueTree.getClass());
        if (parser == null) {
            throw new ConverterException("No value converter found for type " + valueTree.getClass().getSimpleName() + ". Check Tomoko configuration.");
        }
        return parse(valueTree, parser);
    }

    private <T> V parse(ValueTree valueTree, ValueTreeParser<T> parser) {
        return parser.getAs((T) valueTree, valueType);
    }

    @Override
    public String toString() {
        return valueType.toString();
    }

}
