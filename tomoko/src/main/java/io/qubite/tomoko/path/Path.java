package io.qubite.tomoko.path;

import io.qubite.tomoko.util.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Immutable
 */
public class Path {

    private final List<String> nodes;

    Path(List<String> nodes) {
        this.nodes = nodes;
    }

    public static Path parse(String path) {
        if (path.isEmpty()) {
            return empty();
        }
        Preconditions.checkArgument(path.startsWith("/"), "Path must start with a slash character or be empty");
        List<String> nodes = new ArrayList<>(Arrays.asList(path.substring(1).split("/")));
        return new Path(nodes);
    }

    public static Path of(String... nodes) {
        checkSlash(Arrays.asList(nodes));
        return new Path(Arrays.asList(nodes));
    }

    public static Path of(List<String> nodes) {
        checkSlash(nodes);
        return new Path(new ArrayList<>(nodes));
    }

    public static Path empty() {
        return new Path(new ArrayList<>());
    }

    public int getLength() {
        return nodes.size();
    }

    public List<String> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public String getValue(int index) {
        return nodes.get(index);
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public Path prepend(Path path) {
        List<String> newNodes = new ArrayList<>(path.getNodes());
        newNodes.addAll(this.nodes);
        return new Path(newNodes);
    }

    public Path prepend(String value) {
        Preconditions.checkArgument(!value.contains("/"), "Node cannot contain the slash character.");
        List<String> newNodes = new ArrayList<>();
        newNodes.add(value);
        newNodes.addAll(this.nodes);
        return new Path(newNodes);
    }

    public Path append(Path path) {
        List<String> newNodes = new ArrayList<>(this.nodes);
        newNodes.addAll(path.getNodes());
        return new Path(newNodes);
    }

    public Path append(String value) {
        Preconditions.checkArgument(!value.contains("/"), "Node cannot contain the slash character.");
        List<String> newNodes = new ArrayList<>(this.nodes);
        newNodes.add(value);
        return new Path(newNodes);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String nodeName : nodes) {
            builder.append("/");
            builder.append(nodeName);
        }
        return builder.toString();
    }

    private static void checkSlash(List<String> nodes) {
        boolean foundSlash = nodes.stream().anyMatch((node) -> node.contains("/"));
        if (foundSlash) {
            throw new IllegalArgumentException("Node cannot contain the slash character.");
        }
    }

}
