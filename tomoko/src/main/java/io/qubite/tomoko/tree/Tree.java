package io.qubite.tomoko.tree;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.node.PathNode;

import java.util.Map;
import java.util.Optional;

public interface Tree<H> extends Iterable<TreeIterator.TreeEntry<H>> {

    Tree<H> resolve(Path path);

    Optional<? extends Tree<H>> getChild(PathNode pathNode);

    Map<PathNode, ? extends Tree<H>> getChildren();

    Optional<? extends Tree<H>> findMatchingChild(String nodeValue);

    PathNode getPathNode();

    H getHandler();

    boolean isHandlerRegistered();

    boolean isLeaf();


}
