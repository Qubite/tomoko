package io.qubite.tomoko.path.node;

public interface ValueNode<T> extends PathNode<T> {

    default boolean isValueHolder() {
        return true;
    }

}
