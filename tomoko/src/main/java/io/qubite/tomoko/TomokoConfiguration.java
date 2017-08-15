package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ValueTreeParser;

import java.util.Set;

/**
 * Tomoko library configuration. To create one use the builder() method.
 * <br/><br/>
 * Immutable.
 */
public class TomokoConfiguration {

    private final Set<ValueTreeParser<?>> valueTreeParsers;

    TomokoConfiguration(Set<ValueTreeParser<?>> valueTreeParsers) {
        this.valueTreeParsers = valueTreeParsers;
    }

    /**
     * Create a configuration builder.
     *
     * @return
     */
    public static TomokoConfigurationBuilder builder() {
        return TomokoConfigurationBuilder.base();
    }

    public Set<ValueTreeParser<?>> getValueTreeParsers() {
        return valueTreeParsers;
    }


}
