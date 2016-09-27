package io.qubite.tomoko.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by edhendil on 11.08.16.
 * <p>
 * Immutable
 */
public class Path {

    private final List<String> nodes;

    Path(List<String> nodes) {
        this.nodes = nodes;
    }

    public static Path parse(String path) {
        List<String> nodes = new ArrayList<>(Arrays.asList(path.split("/")));
        nodes.remove(0);
        return new Path(nodes);
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

}
