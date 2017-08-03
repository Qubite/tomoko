package io.qubite.tomoko.configuration;

import io.qubite.tomoko.path.converter.PathParameterConverter;

public class ParameterConfiguration<T> {

    private final String parameterName;
    private final PathParameterConverter<T> converter;

    ParameterConfiguration(String parameterName, PathParameterConverter<T> converter) {
        this.parameterName = parameterName;
        this.converter = converter;
    }

    public static <T> ParameterConfiguration<T> of(String parameterName, PathParameterConverter<T> converter) {
        return new ParameterConfiguration<>(parameterName, converter);
    }

    public static <T> ParameterConfiguration<T> of(String parameterName) {
        return new ParameterConfiguration<>(parameterName, null);
    }

    public String getParameterName() {
        return parameterName;
    }

    public PathParameterConverter<T> getConverter() {
        return converter;
    }

}
