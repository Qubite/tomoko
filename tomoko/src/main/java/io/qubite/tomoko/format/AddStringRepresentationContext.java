package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.tree.TreeNode;

/**
 * Created by edhendil on 30.08.16.
 */
public class AddStringRepresentationContext {

    private final PathTemplate<?> pathTemplate;
    private final TreeNode<AddHandler<?>> node;

    AddStringRepresentationContext(PathTemplate<?> pathTemplate, TreeNode<AddHandler<?>> node) {
        this.pathTemplate = pathTemplate;
        this.node = node;
    }

    public static AddStringRepresentationContext of(PathTemplate<?> pathTemplate, TreeNode<AddHandler<?>> node) {
        return new AddStringRepresentationContext(pathTemplate, node);
    }

    public PathTemplate<?> getPathTemplate() {
        return pathTemplate;
    }

    public TreeNode<AddHandler<?>> getNode() {
        return node;
    }

    public AddStringRepresentationContext with(PathNode pathNode, TreeNode<AddHandler<?>> treeNode) {
        return new AddStringRepresentationContext(pathTemplate.then(pathNode), treeNode);
    }
}
