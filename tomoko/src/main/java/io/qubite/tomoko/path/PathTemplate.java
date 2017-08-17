package io.qubite.tomoko.path;

import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.specification.scanner.PatternElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable
 */
public class PathTemplate {

    private final List<PathNode> nodes;

    PathTemplate(List<PathNode> nodes) {
        this.nodes = nodes;
    }

    public static PathTemplate empty() {
        return new PathTemplate(new ArrayList<>());
    }

    public static PathTemplate from(PathPattern pathPattern) {
        PathTemplate result = PathTemplate.empty();
        for (PatternElement element : pathPattern) {
            PathNode node = from(element);
            result = result.append(node);
        }
        return result;
    }

    private static PathNode from(PatternElement element) {
        PathNode result;
        if (element.isFixed()) {
            result = PathNodes.staticNode(element.getName());
        } else if (element.isWildcard()) {
            result = PathNodes.textNode();
        } else {
            result = PathNodes.regexNode(element.getRegex());
        }
        return result;
    }

    public List<PathNode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public int length() {
        return nodes.size();
    }

    public PathTemplate append(PathNode pathNode) {
        List<PathNode> newNodeChain = createNewChain(pathNode);
        return new PathTemplate(newNodeChain);
    }

    public PathTemplate append(PathTemplate pathTemplate) {
        List<PathNode> newNodeChain = createNewChain(pathTemplate);
        return new PathTemplate(newNodeChain);
    }

    private List<PathNode> createNewChain(PathNode chained) {
        List<PathNode> newNodes = new ArrayList<>();
        newNodes.addAll(nodes);
        newNodes.add(chained);
        return newNodes;
    }

    private List<PathNode> createNewChain(PathTemplate chained) {
        List<PathNode> newNodes = new ArrayList<>();
        newNodes.addAll(nodes);
        newNodes.addAll(chained.getNodes());
        return newNodes;
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
        for (PathNode node : nodes) {
            builder.append(node.toString());
        }
        return builder.toString();
    }

}
