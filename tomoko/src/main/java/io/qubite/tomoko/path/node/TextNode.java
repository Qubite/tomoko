package io.qubite.tomoko.path.node;

import java.util.Objects;

/**
 * Created by edhendil on 03.09.16.
 */
public class TextNode implements ValueNode<String> {

    TextNode() {
    }

    @Override
    public boolean doesMatch(String value) {
        return true;
    }

    @Override
    public String toObject(String value) {
        return value;
    }

    @Override
    public String toPathString(String value) {
        return value;
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
        return "/(string)";
    }

}
