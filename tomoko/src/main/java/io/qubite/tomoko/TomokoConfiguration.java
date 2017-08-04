package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ValueParser;

import java.util.Set;

/**
 * Tomoko library configuration. To create one use the builder() method.
 * <br/><br/>
 * Immutable.
 */
public class TomokoConfiguration {

    private final Set<ValueParser<?>> valueParsers;

    TomokoConfiguration(Set<ValueParser<?>> valueParsers) {
        this.valueParsers = valueParsers;
    }

    /**
     * Create a configuration builder.
     *
     * @return
     */
    public static TomokoConfigurationBuilder builder() {
        return TomokoConfigurationBuilder.base();
    }

    public Set<ValueParser<?>> getValueParsers() {
        return valueParsers;
    }


}
