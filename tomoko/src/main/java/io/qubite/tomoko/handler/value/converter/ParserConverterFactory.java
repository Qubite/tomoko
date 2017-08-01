package io.qubite.tomoko.handler.value.converter;

import io.qubite.tomoko.TomokoConfiguration;
import io.qubite.tomoko.type.ValueType;

import java.util.Set;

public class ParserConverterFactory implements ValueConverterFactory {

    private final TomokoConfiguration tomokoConfiguration;

    ParserConverterFactory(TomokoConfiguration tomokoConfiguration) {
        this.tomokoConfiguration = tomokoConfiguration;
    }

    public static ParserConverterFactory instance(TomokoConfiguration tomokoConfiguration) {
        return new ParserConverterFactory(tomokoConfiguration);
    }

    @Override
    public <T> ValueConverter<T> forType(ValueType<T> valueType) {
        Set<ValueParser<?>> valueParsers = tomokoConfiguration.getValueParsers();
        if (valueParsers.size() == 1) {
            return SingleParserConverter.of(valueParsers.stream().findAny().get(), valueType);
        } else {
            return MultipleParsersConverter.of(valueType, valueParsers);
        }
    }

}
