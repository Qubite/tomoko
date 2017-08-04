package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ValueParser;
import io.qubite.tomoko.util.Preconditions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Tomoko configuration builder.
 */
public class TomokoConfigurationBuilder {

    private final Set<ValueParser<?>> valueParsers = new HashSet<>();

    public static TomokoConfigurationBuilder base() {
        return new TomokoConfigurationBuilder();
    }

    /**
     * Registers a value parser that will be used to parse values provided in ADD and REPLACE operations. Only one parser can be registered for each type to be parsed.
     *
     * @param parser
     * @param <T>
     * @return
     */
    public <T> TomokoConfigurationBuilder registerValueParser(ValueParser<T> parser) {
        Preconditions.checkNotNull(parser);
        if (valueParsers.stream().anyMatch((existing) -> existing.supportedType().equals(parser.supportedType()))) {
            throw new IllegalArgumentException("Cannot register two value parsers for the same type: " + parser.supportedType());
        }
        valueParsers.add(parser);
        return this;
    }

    /**
     * Removes all registered value parsers.
     * @return
     */
    public TomokoConfigurationBuilder clearValueParsers() {
        valueParsers.clear();
        return this;
    }

    /**
     * Returns a new Tomoko configuration instance. At least one value parser must be registered.
     * @return
     */
    public TomokoConfiguration build() {
        if (valueParsers.isEmpty()) {
            throw new IllegalStateException("There must be at least one value parser registered.");
        }
        return new TomokoConfiguration(Collections.unmodifiableSet(this.valueParsers));
    }

}
