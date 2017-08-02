package io.qubite.tomoko.path.node;

import java.util.Objects;

public class TextNode implements PathNode {

    TextNode() {
    }

    @Override
    public boolean doesMatch(String value) {
        return true;
    }

    @Override
    public int classOrder() {
        return 30;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextNode)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return "/{arg}";
    }

    @Override
    public int compareTo(PathNode pathNode) {
        if (!pathNode.getClass().equals(TextNode.class)) {
            int result = classOrder() - pathNode.classOrder();
            if (result == 0) {
                throw new IllegalStateException("Two path node implementation cannot have the same order value.");
            }
            return result;
        } else {
            return 0;
        }
    }

}
