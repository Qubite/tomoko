package io.qubite.tomoko.format;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.tree.TreeNode;

public class StringRepresentationContext<T> {

    private final PathTemplate pathTemplate;
    private final TreeNode<T> node;

    StringRepresentationContext(PathTemplate pathTemplate, TreeNode<T> node) {
        this.pathTemplate = pathTemplate;
        this.node = node;
    }

    public static <T> StringRepresentationContext of(PathTemplate pathTemplate, TreeNode<T> node) {
        return new StringRepresentationContext<>(pathTemplate, node);
    }

    public PathTemplate getPathTemplate() {
        return pathTemplate;
    }

    public TreeNode<T> getNode() {
        return node;
    }

    public StringRepresentationContext with(PathNode pathNode, TreeNode<T> treeNode) {
        return new StringRepresentationContext(pathTemplate.append(pathNode), treeNode);
    }
}
