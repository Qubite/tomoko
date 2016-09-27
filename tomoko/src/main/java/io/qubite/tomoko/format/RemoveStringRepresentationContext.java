package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.tree.TreeNode;

/**
 * Created by edhendil on 30.08.16.
 */
public class RemoveStringRepresentationContext {

    private final PathTemplate<?> pathTemplate;
    private final TreeNode<RemoveHandler> node;

    RemoveStringRepresentationContext(PathTemplate<?> pathTemplate, TreeNode<RemoveHandler> node) {
        this.pathTemplate = pathTemplate;
        this.node = node;
    }

    public static RemoveStringRepresentationContext of(PathTemplate<?> pathTemplate, TreeNode<RemoveHandler> node) {
        return new RemoveStringRepresentationContext(pathTemplate, node);
    }

    public PathTemplate<?> getPathTemplate() {
        return pathTemplate;
    }

    public TreeNode<RemoveHandler> getNode() {
        return node;
    }

    public RemoveStringRepresentationContext with(PathNode pathNode, TreeNode<RemoveHandler> treeNode) {
        return new RemoveStringRepresentationContext(pathTemplate.then(pathNode), treeNode);
    }
}
