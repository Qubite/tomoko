package io.qubite.tomoko;

import io.qubite.tomoko.direct.DirectTreeParser;
import io.qubite.tomoko.handler.value.converter.ValueParser;

import java.util.HashSet;
import java.util.Set;

public class TomokoConfiguration {

    private final Set<ValueParser<?>> valueParsers;

    TomokoConfiguration(Set<ValueParser<?>> valueParsers) {
        this.valueParsers = valueParsers;
    }

    public static TomokoConfiguration direct() {
        TomokoConfiguration configuration = empty();
        configuration.valueParsers.add(new DirectTreeParser());
        return configuration;
    }

    public static TomokoConfiguration empty() {
        return new TomokoConfiguration(new HashSet<>());
    }

    public <T> void registerValueParser(ValueParser<T> parser) {
        valueParsers.add(parser);
    }

    public Set<ValueParser<?>> getValueParsers() {
        return valueParsers;
    }
}
