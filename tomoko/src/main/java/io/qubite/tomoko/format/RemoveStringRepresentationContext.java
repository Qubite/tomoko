package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.tree.TreeNode;

public class RemoveStringRepresentationContext {

    private final PathTemplate<?> pathTemplate;
    private final TreeNode<ValuelessHandler> node;

    RemoveStringRepresentationContext(PathTemplate<?> pathTemplate, TreeNode<ValuelessHandler> node) {
        this.pathTemplate = pathTemplate;
        this.node = node;
    }

    public static RemoveStringRepresentationContext of(PathTemplate<?> pathTemplate, TreeNode<ValuelessHandler> node) {
        return new RemoveStringRepresentationContext(pathTemplate, node);
    }

    public PathTemplate<?> getPathTemplate() {
        return pathTemplate;
    }

    public TreeNode<ValuelessHandler> getNode() {
        return node;
    }

    public RemoveStringRepresentationContext with(PathNode pathNode, TreeNode<ValuelessHandler> treeNode) {
        return new RemoveStringRepresentationContext(pathTemplate.then(pathNode), treeNode);
    }
}
