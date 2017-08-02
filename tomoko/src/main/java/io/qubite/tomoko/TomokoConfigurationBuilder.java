package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ValueParser;
import io.qubite.tomoko.util.Preconditions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TomokoConfigurationBuilder {

    private final Set<ValueParser<?>> valueParsers = new HashSet<>();

    public static TomokoConfigurationBuilder base() {
        return new TomokoConfigurationBuilder();
    }

    public <T> TomokoConfigurationBuilder registerValueParser(ValueParser<T> parser) {
        Preconditions.checkNotNull(parser);
        if (valueParsers.stream().anyMatch((existing) -> existing.supportedType().equals(parser.supportedType()))) {
            throw new IllegalArgumentException("Cannot register two value parsers for the same type: " + parser.supportedType());
        }
        valueParsers.add(parser);
        return this;
    }

    public TomokoConfigurationBuilder clearValueParsers() {
        valueParsers.clear();
        return this;
    }

    public TomokoConfiguration build() {
        if (valueParsers.isEmpty()) {
            throw new IllegalStateException("There must be at least one value parser registered.");
        }
        return new TomokoConfiguration(Collections.unmodifiableSet(this.valueParsers));
    }

}
