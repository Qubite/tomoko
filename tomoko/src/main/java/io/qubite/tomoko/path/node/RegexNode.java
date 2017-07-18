package io.qubite.tomoko.path.node;

import java.util.Objects;

public class RegexNode implements ValueNode<String> {

    private final String regex;

    RegexNode(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean doesMatch(String value) {
        return value.matches(regex);
    }

    @Override
    public String toObject(String value) {
        return value;
    }

    public String toPathString(String value) {
        if (!doesMatch(value)) {
            throw new IllegalArgumentException("Provided value does not match regex.");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegexNode)) {
            return false;
        }
        RegexNode that = (RegexNode) o;
        return this.regex.equals(that.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regex);
    }

    @Override
    public String toString() {
        return "/(regex:" + regex + ")";
    }
}
