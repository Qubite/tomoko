package io.qubite.tomoko.path.node;

public interface PathNode<T> {

    boolean doesMatch(String value);

    boolean isValueHolder();

    T toObject(String value);

    String toPathString(T value);

}
