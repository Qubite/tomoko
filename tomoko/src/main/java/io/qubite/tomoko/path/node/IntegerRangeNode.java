package io.qubite.tomoko.path.node;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by edhendil on 03.09.16.
 */
public class IntegerRangeNode implements ValueNode<Integer> {

    private final Optional<Integer> minValue;
    private final Optional<Integer> maxValue;

    IntegerRangeNode(Optional<Integer> minValue, Optional<Integer> maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean doesMatch(String value) {
        try {
            int result = Integer.parseInt(value);
            if (minValue.isPresent() && minValue.get() > result) {
                return false;
            }
            if (maxValue.isPresent() && maxValue.get() < result) {
                return false;
            }
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
        if (minValue.isPresent() && minValue.get() > value) {
            throw new IllegalArgumentException("Value lower than specified minimum.");
        }
        if (maxValue.isPresent() && maxValue.get() < value) {
            throw new IllegalArgumentException("Value higher than specified maximum.");
        }
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegerRangeNode)) {
            return false;
        }
        IntegerRangeNode that = (IntegerRangeNode) o;
        return minValue.equals(that.minValue) && maxValue.equals(that.maxValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minValue, maxValue);
    }

    @Override
    public String toString() {
        String min = "";
        String max = "";
        if (minValue.isPresent()) {
            min = minValue.get() + "<=";
        }
        if (maxValue.isPresent()) {
            max = ">=" + maxValue.get();
        }
        return "/(" + min + "integer" + max + ")";
    }
}
