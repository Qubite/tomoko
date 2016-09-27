package io.qubite.tomoko.path.node;

/**
 * Created by edhendil on 29.08.16.
 */
public interface ValueNode<T> extends PathNode<T> {

    default boolean isValueHolder() {
        return true;
    }

}
