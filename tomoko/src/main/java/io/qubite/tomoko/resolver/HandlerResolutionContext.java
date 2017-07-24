package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.tree.TreeNode;

class HandlerResolutionContext {

    private final Path path;
    private final ValueTree value;
    private final TreeNode<ValueHandler> node;

    HandlerResolutionContext(Path path, ValueTree value, TreeNode<ValueHandler> node) {
        this.path = path;
        this.value = value;
        this.node = node;
    }

    public static HandlerResolutionContext of(Path path, ValueTree value, TreeNode<ValueHandler> node) {
        return new HandlerResolutionContext(path, value, node);
    }

    public Path getPath() {
        return path;
    }

    public ValueTree getValue() {
        return value;
    }

    public TreeNode<ValueHandler> getNode() {
        return node;
    }

}
