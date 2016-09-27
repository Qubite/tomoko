package io.qubite.tomoko.path.node;

import java.util.Objects;

/**
 * Created by edhendil on 12.08.16.
 */
public class IntegerNode implements ValueNode<Integer> {

    @Override
    public boolean doesMatch(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Integer toObject(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String toPathString(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Null not accepted.");
        }
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegerNode)) {
            return false;
        }
        IntegerNode that = (IntegerNode) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return "/(integer)";
    }
}
