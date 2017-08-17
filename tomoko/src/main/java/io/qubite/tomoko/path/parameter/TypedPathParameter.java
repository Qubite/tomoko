package io.qubite.tomoko.path.parameter;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.converter.PathParameterConverter;

public class TypedPathParameter<T> implements PathParameter<T> {

    private final PathParameter<String> originalParameter;
    private final PathParameterConverter<T> converter;

    TypedPathParameter(PathParameter<String> originalParameter, PathParameterConverter<T> converter) {
        this.originalParameter = originalParameter;
        this.converter = converter;
    }

    public static <T> TypedPathParameter<T> of(PathParameter<String> originalParameter, PathParameterConverter<T> converter) {
        return new TypedPathParameter<>(originalParameter, converter);
    }

    public T extractValue(Path path) {
        return converter.toObject(originalParameter.extractValue(path));
    }

}
