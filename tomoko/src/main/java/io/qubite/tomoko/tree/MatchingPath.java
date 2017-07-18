package io.qubite.tomoko.tree;

import io.qubite.tomoko.path.PathParameters;

public class MatchingPath<H> {

    private final PathParameters pathParameters;
    private final TreeNode<H> node;

    MatchingPath(PathParameters pathParameters, TreeNode<H> node) {
        this.pathParameters = pathParameters;
        this.node = node;
    }

    public static <H> MatchingPath of(PathParameters pathParameters, TreeNode<H> node) {
        return new MatchingPath(pathParameters, node);
    }

    public TreeNode<H> getNode() {
        return node;
    }

    public PathParameters getPathParameters() {
        return pathParameters;
    }

}
