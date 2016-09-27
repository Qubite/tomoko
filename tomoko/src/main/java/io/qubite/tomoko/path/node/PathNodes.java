package io.qubite.tomoko.path.node;

import java.util.Optional;

public class PathNodes {

    private PathNodes() {
    }

    public static StaticNode staticNode(String text) {
        return new StaticNode(text);
    }

    public static StaticNode endIndexNode() {
        return new StaticNode("-");
    }

    public static StaticNode rootNode() {
        return new StaticNode("");
    }

    public static RegexNode regexNode(String regex) {
        return new RegexNode(regex);
    }

    public static TextNode textNode() {
        return new TextNode();
    }

    public static IntegerNode integerNode() {
        return new IntegerNode();
    }

    public static LongNode longNode() {
        return new LongNode();
    }

    public static IntegerRangeNode integerNode(int min, int max) {
        return new IntegerRangeNode(Optional.of(min), Optional.of(max));
    }

    public static IntegerRangeNode minIntegerNode(int min) {
        return new IntegerRangeNode(Optional.of(min), Optional.empty());
    }

    public static IntegerRangeNode maxIntegerNode(int max) {
        return new IntegerRangeNode(Optional.empty(), Optional.of(max));
    }

}
