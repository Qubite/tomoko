package io.qubite.tomoko.path.node;

public interface ValuelessNode extends PathNode<Void> {

    default boolean isValueHolder() {
        return false;
    }

    default Void toObject(String value) {
        return null;
    }

}
