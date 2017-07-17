package io.qubite.tomoko.path.node;

import java.util.Objects;

/**
 * Created by edhendil on 03.09.16.
 */
public class IntegerRangeNode implements ValueNode<Integer> {

    private final int minValue;
    private final int maxValue;

    IntegerRangeNode(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean doesMatch(String value) {
        try {
            int result = Integer.parseInt(value);
            if (minValue > result) {
                return false;
            }
            if (maxValue < result) {
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
        if (minValue > value) {
            throw new IllegalArgumentException("Value lower than specified minimum.");
        }
        if (maxValue < value) {
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
        return minValue == that.minValue && maxValue == that.maxValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minValue, maxValue);
    }

    @Override
    public String toString() {
        String min = "";
        String max = "";
        if (minValue != Integer.MIN_VALUE) {
            min = minValue + "<=";
        }
        if (maxValue != Integer.MAX_VALUE) {
            max = ">=" + maxValue;
        }
        return "/(" + min + "integer" + max + ")";
    }
}
