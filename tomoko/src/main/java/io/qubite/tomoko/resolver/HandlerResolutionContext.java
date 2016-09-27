package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.tree.TreeNode;

/**
 * Created by edhendil on 15.08.16.
 */
class HandlerResolutionContext {

    private final PathParameters pathParameters;
    private final JsonTree value;
    private final TreeNode<AddHandler<?>> node;

    HandlerResolutionContext(PathParameters pathParameters, JsonTree value, TreeNode<AddHandler<?>> node) {
        this.pathParameters = pathParameters;
        this.value = value;
        this.node = node;
    }

    public static HandlerResolutionContext of(PathParameters pathParameters, JsonTree value, TreeNode<AddHandler<?>> node) {
        return new HandlerResolutionContext(pathParameters, value, node);
    }

    public PathParameters getPathParameters() {
        return pathParameters;
    }

    public JsonTree getValue() {
        return value;
    }

    public TreeNode<AddHandler<?>> getNode() {
        return node;
    }

}
