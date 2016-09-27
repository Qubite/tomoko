package io.qubite.tomoko.path.node;

/**
 * Created by edhendil on 29.08.16.
 */
public interface ValuelessNode extends PathNode<Void> {

    default boolean isValueHolder() {
        return false;
    }

    default Void toObject(String value) {
        return null;
    }

}
