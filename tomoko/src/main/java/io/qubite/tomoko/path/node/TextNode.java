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

}
