package io.qubite.tomoko.path.node;

import java.util.regex.Pattern;

public class PathNodes {

    private PathNodes() {
    }

    public static PathNode staticNode(String text) {
        return new StaticNode(text);
    }

    public static PathNode endIndexNode() {
        return staticNode("-");
    }

    public static PathNode rootNode() {
        return staticNode("");
    }

    public static PathNode regexNode(String regex) {
        return new RegexNode(Pattern.compile(regex));
    }

    public static PathNode textNode() {
        return new TextNode();
    }

    public static PathNode integerNode() {
        return regexNode("^[0-9]+$");
    }

}
