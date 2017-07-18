package io.qubite.tomoko.tree;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.node.PathNode;

import java.util.Map;
import java.util.Optional;

public interface Tree<H> {

    PathNode getPathNode();

    Optional<? extends Tree<H>> getChild(PathNode pathNode);

    Map<PathNode, TreeNode<H>> getChildren();

    Optional<? extends Tree<H>> findMatchingChild(String nodeValue);

    H getHandler();

    boolean isHandlerRegistered();

    boolean isLeaf();

    MatchingPath<H> resolve(Path path);

}
