package io.qubite.tomoko.tree;

import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.path.node.PathNodes;

import java.util.Optional;
import java.util.TreeMap;

public class TreeNodes {

    private TreeNodes() {
    }

    public static <H> TreeNode<H> full(PathNode node, H handler) {
        return new TreeNode(node, new TreeMap<>(), Optional.of(handler));
    }

    public static <H> TreeNode<H> empty(PathNode node) {
        return new TreeNode(node, new TreeMap<>(), Optional.empty());
    }

    public static <H> TreeNode<H> root() {
        return new TreeNode<>(PathNodes.rootNode(), new TreeMap<>(), Optional.empty());
    }

}
