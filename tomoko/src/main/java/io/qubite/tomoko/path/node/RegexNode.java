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
    public int classOrder() {
        return 20;
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
        return "/{arg:" + regex + "}";
    }

    @Override
    public int compareTo(PathNode pathNode) {
        if (!pathNode.getClass().equals(RegexNode.class)) {
            int result = classOrder() - pathNode.classOrder();
            if (result == 0) {
                throw new IllegalStateException("Two path node implementation cannot have the same order value.");
            }
            return result;
        } else {
            RegexNode that = (RegexNode) pathNode;
            return regex.toString().compareTo(that.regex.toString());
        }
    }

}
