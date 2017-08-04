package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.path.converter.Converters;
import io.qubite.tomoko.path.converter.PathParameterConverter;

public class ParameterDescriptor<T> {

    private final String name;
    private final PathParameterConverter<T> converter;

    public ParameterDescriptor(String name, PathParameterConverter<T> converter) {
        this.name = name;
        this.converter = converter;
    }

    public static <T> ParameterDescriptor<T> of(String name, PathParameterConverter<T> converter) {
        return new ParameterDescriptor<>(name, converter);
    }

    public static ParameterDescriptor<String> of(String name) {
        return new ParameterDescriptor<>(name, Converters.identity());
    }

    public String getName() {
        return name;
    }

    public PathParameterConverter<T> getConverter() {
        return converter;
    }

    public String getParameterValue(T value) {
        return converter.toPathString(value);
    }

}
