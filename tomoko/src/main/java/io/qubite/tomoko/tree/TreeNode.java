package io.qubite.tomoko.tree;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;

import java.util.Map;
import java.util.Optional;

/**
 * Created by edhendil on 13.08.16.
 */
public class TreeNode<H> implements Tree<H> {

    private final PathNode pathNode;
    private final Map<PathNode, TreeNode<H>> children;
    private Optional<H> handlerOptional;

    public TreeNode(PathNode pathNode, Map<PathNode, TreeNode<H>> children, Optional<H> handler) {
        this.pathNode = pathNode;
        this.children = children;
        this.handlerOptional = handler;
    }

    public PathNode getPathNode() {
        return pathNode;
    }

    public Optional<TreeNode<H>> getChild(PathNode pathNode) {
        TreeNode treeNode = children.get(pathNode);
        return Optional.ofNullable(treeNode);
    }

    public Map<PathNode, TreeNode<H>> getChildren() {
        return children;
    }

    public Optional<TreeNode<H>> findMatchingChild(String nodeValue) {
        return children.entrySet().stream().filter((entry) -> entry.getKey().doesMatch(nodeValue)).findFirst().map(Map.Entry::getValue);
    }

    public void addChild(TreeNode<H> treeNode) {
        checkIfCanAdd(treeNode.getPathNode());
        children.put(treeNode.getPathNode(), treeNode);
    }

    public H getHandler() {
        return handlerOptional.get();
    }

    public void setHandler(H handler) {
        this.handlerOptional = Optional.of(handler);
    }

    public boolean isHandlerRegistered() {
        return handlerOptional.isPresent();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public TreeNode<H> extend(PathTemplate<?> pathTemplate) {
        TreeNode<H> parent = this;
        for (PathNode<?> pathNode : pathTemplate.getNodes()) {
            Optional<TreeNode<H>> current = parent.getChild(pathNode);
            if (current.isPresent()) {
                parent = current.get();
            } else {
                TreeNode created = TreeNodes.full(pathNode);
                parent.addChild(created);
                parent = created;
            }
        }
        return parent;
    }

    public TreeNode<H> extendUntilHandler(PathTemplate<?> pathTemplate) {
        TreeNode<H> parent = this;
        for (PathNode<?> pathNode : pathTemplate.getNodes()) {
            if (parent.isHandlerRegistered()) {
                throw new PatcherException("Cannot extend the path as there is a handler registered on the way.");
            }
            Optional<TreeNode<H>> current = parent.getChild(pathNode);
            if (current.isPresent()) {
                parent = current.get();
            } else {
                TreeNode created = TreeNodes.full(pathNode);
                parent.addChild(created);
                parent = created;
            }
        }
        return parent;
    }

    public MatchingPath<H> resolve(Path path) {
        TreeNode<H> parent = this;
        PathParameters.Builder builder = PathParameters.builder();
        for (String nodeValue : path.getNodes()) {
            Optional<TreeNode<H>> optionalMatchingChild = parent.findMatchingChild(nodeValue);
            if (optionalMatchingChild.isPresent()) {
                parent = optionalMatchingChild.get();
                builder.addMapping(parent.getPathNode(), nodeValue);
            } else {
                throw new PatcherException("Cannot resolve a given path. No matching node found.");
            }
        }
        return MatchingPath.of(builder.build(), parent);
    }

    private void checkIfCanAdd(PathNode pathNode) {
        if (children.get(pathNode) != null) {
            throw new IllegalArgumentException("Node already added: " + pathNode);
        }
    }

}
