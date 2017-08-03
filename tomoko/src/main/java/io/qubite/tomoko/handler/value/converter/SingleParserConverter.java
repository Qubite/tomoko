package io.qubite.tomoko.handler.value.converter;

import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.type.ValueType;

public class SingleParserConverter<V, P> implements ValueConverter<V> {

    private final ValueParser<P> parser;
    private final ValueType<V> valueType;

    SingleParserConverter(ValueParser<P> parser, ValueType<V> valueType) {
        this.parser = parser;
        this.valueType = valueType;
    }

    public static <V, P> SingleParserConverter<V, P> of(ValueParser<P> parser, ValueType<V> valueType) {
        return new SingleParserConverter<>(parser, valueType);
    }

    @Override
    public V parse(ValueTree valueTree) {
        if (!parser.supportedType().isInstance(valueTree)) {
            throw new ConverterException("No value converter found for type " + valueTree.getClass().getSimpleName() + ". Check Tomoko configuration.");
        }
        return parser.getAs((P) valueTree, valueType);
    }

    @Override
    public String toString() {
        return valueType.toString();
    }

}
