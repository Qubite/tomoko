package io.qubite.tomoko.path;

import io.qubite.tomoko.path.node.PathNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable.
 */
public class PathParameters {

    private final List<Entry<?>> valueByPathNode;

    PathParameters(List<Entry<?>> valueByPathNode) {
        this.valueByPathNode = valueByPathNode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T getValue(PathTemplate<T> parameterPath) {
        checkIsPathValid(parameterPath);
        return (T) valueByPathNode.get(parameterPath.size() - 1).getValue();
    }

    private <T> void checkIsPathValid(PathTemplate<T> parameterPath) {
        List<PathNode<?>> parameterPathNodes = parameterPath.getNodes();
        for (int i = 0; i < parameterPathNodes.size(); i++) {
            boolean matching = valueByPathNode.get(i).getPathNode().equals(parameterPathNodes.get(i));
            if (!matching) {
                throw new IllegalArgumentException("Parameter path is not a part of the current path.");
            }
        }
    }

    public <T> PathParameters add(PathNode<T> node, String nodeValue) {
        if (!node.doesMatch(nodeValue)) {
            throw new IllegalArgumentException("Tried to map node to a not matching value.");
        }
        List<Entry<?>> newEntries = new ArrayList<>(valueByPathNode);
        T finalValue = node.toObject(nodeValue);
        newEntries.add(Entry.of(node, finalValue));
        return new PathParameters(newEntries);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Entry<?> entry : valueByPathNode) {
            builder.append("/");
            builder.append(entry.getValue());
        }
        return builder.toString();
    }

    public static class Builder {

        private final List<Entry<?>> valuesByNode = new ArrayList<>();

        public <T> Builder addMapping(PathNode<T> node, String nodeValue) {
            if (!node.doesMatch(nodeValue)) {
                throw new IllegalArgumentException("Tried to map node to a not matching value.");
            }
            T finalValue = node.toObject(nodeValue);
            valuesByNode.add(Entry.of(node, finalValue));
            return this;
        }

        public PathParameters build() {
            return new PathParameters(valuesByNode);
        }

    }

    private static class Entry<T> {

        private final PathNode<T> pathNode;
        private final T value;

        Entry(PathNode<T> pathNode, T value) {
            this.pathNode = pathNode;
            this.value = value;
        }

        public static <T> Entry<T> of(PathNode<T> pathNode, T value) {
            return new Entry<>(pathNode, value);
        }

        public PathNode<T> getPathNode() {
            return pathNode;
        }

        public T getValue() {
            return value;
        }

    }

}
