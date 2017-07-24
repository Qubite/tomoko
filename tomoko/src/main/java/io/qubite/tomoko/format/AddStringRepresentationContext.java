package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.tree.TreeNode;

public class AddStringRepresentationContext {

    private final PathTemplate pathTemplate;
    private final TreeNode<ValueHandler> node;

    AddStringRepresentationContext(PathTemplate pathTemplate, TreeNode<ValueHandler> node) {
        this.pathTemplate = pathTemplate;
        this.node = node;
    }

    public static AddStringRepresentationContext of(PathTemplate pathTemplate, TreeNode<ValueHandler> node) {
        return new AddStringRepresentationContext(pathTemplate, node);
    }

    public PathTemplate getPathTemplate() {
        return pathTemplate;
    }

    public TreeNode<ValueHandler> getNode() {
        return node;
    }

    public AddStringRepresentationContext with(PathNode pathNode, TreeNode<ValueHandler> treeNode) {
        return new AddStringRepresentationContext(pathTemplate.append(pathNode), treeNode);
    }
}
