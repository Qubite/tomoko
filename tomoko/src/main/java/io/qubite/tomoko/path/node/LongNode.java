package io.qubite.tomoko.path.node;

import java.util.Objects;

/**
 * Created by edhendil on 03.09.16.
 */
public class LongNode implements ValueNode<Long> {

    @Override
    public boolean doesMatch(String value) {
        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Long toObject(String value) {
        return Long.parseLong(value);
    }

    @Override
    public String toPathString(Long value) {
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
        if (!(o instanceof LongNode)) {
            return false;
        }
        LongNode that = (LongNode) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return "/(long)";
    }

}
