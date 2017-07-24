package io.qubite.tomoko.path.node;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegexNode implements PathNode {

    private final Pattern regex;

    RegexNode(Pattern regex) {
        this.regex = regex;
    }

    @Override
    public boolean doesMatch(String value) {
        return regex.matcher(value).find();
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
