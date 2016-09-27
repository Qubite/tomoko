package io.qubite.tomoko.path;

import io.qubite.tomoko.path.node.PathNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable
 *
 * @param <T>
 */
public class PathTemplate<T> {

    private final List<PathNode<?>> nodes;

    PathTemplate(List<PathNode<?>> nodes) {
        this.nodes = nodes;
    }

    public static <T> PathTemplate<T> empty() {
        return new PathTemplate(new ArrayList<>());
    }

    public List<PathNode<?>> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public Path generatePath(PathTemplateParameters parameters) {
        Path result = Path.empty();
        int i = 0;
        // intentionally omitted generics
        for (PathNode node : nodes) {
            String nodeToAppend = node.toPathString(parameters.getParameter(i));
            result = result.append(nodeToAppend);
            i++;
        }
        return result;
    }

    public int size() {
        return nodes.size();
    }

    public boolean isSubpath(PathTemplate<?> subpath) {
        List<PathNode<?>> subpathNodes = subpath.getNodes();
        List<PathNode<?>> thisNodes = this.getNodes();
        if (subpath.size() > this.size()) {
            return false;
        }
        for (int i = 0; i < subpathNodes.size(); i++) {
            if (!thisNodes.get(i).equals(subpathNodes.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(PathNode<?> node) {
        return nodes.contains(node);
    }

    public <NT> PathTemplate<NT> then(PathNode<NT> pathNode) {
        List<PathNode<?>> newNodeChain = createNewChain(pathNode);
        return new PathTemplate(newNodeChain);
    }

    public <NT> PathTemplate<NT> then(PathTemplate<NT> pathTemplate) {
        List<PathNode<?>> newNodeChain = createNewChain(pathTemplate);
        return new PathTemplate(newNodeChain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PathTemplate)) {
            return false;
        }
        PathTemplate that = (PathTemplate) o;
        return this.nodes.equals(that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PathNode<?> node : nodes) {
            builder.append(node.toString());
        }
        return builder.toString();
    }

    private List<PathNode<?>> createNewChain(PathNode chained) {
        List<PathNode<?>> newNodes = new ArrayList<>();
        newNodes.addAll(nodes);
        newNodes.add(chained);
        return newNodes;
    }

    private List<PathNode<?>> createNewChain(PathTemplate chained) {
        List<PathNode<?>> newNodes = new ArrayList<>();
        newNodes.addAll(nodes);
        newNodes.addAll(chained.getNodes());
        return newNodes;
    }

}
