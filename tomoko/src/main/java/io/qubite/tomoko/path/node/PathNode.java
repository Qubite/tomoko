package io.qubite.tomoko.path.node;

public interface PathNode extends Comparable<PathNode> {

    boolean doesMatch(String value);

    int classOrder();

}
