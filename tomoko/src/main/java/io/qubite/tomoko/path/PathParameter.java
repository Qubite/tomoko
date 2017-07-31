package io.qubite.tomoko.path;

public interface PathParameter<T> {

    T extractValue(Path path);

}
