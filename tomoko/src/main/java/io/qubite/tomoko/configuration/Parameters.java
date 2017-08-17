package io.qubite.tomoko.configuration;

import io.qubite.tomoko.path.converter.Converters;

public final class Parameters {

    public static <T> ParameterConfiguration<T> string(String name) {
        return ParameterConfiguration.of(name);
    }

    public static ParameterConfiguration<Integer> integer(String name) {
        return ParameterConfiguration.of(name, Converters.integer());
    }

    public static ParameterConfiguration<Long> longParameter(String name) {
        return ParameterConfiguration.of(name, Converters.longConverter());
    }

    public static ParameterConfiguration<String> urlEncoded(String name) {
        return ParameterConfiguration.of(name, Converters.urlEncoded());
    }

}
