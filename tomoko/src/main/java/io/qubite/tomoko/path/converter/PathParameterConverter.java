package io.qubite.tomoko.path.converter;

public interface PathParameterConverter<T> {

    T toObject(String value);

    String toPathString(T value);

}
