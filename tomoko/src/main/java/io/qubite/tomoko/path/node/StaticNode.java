package io.qubite.tomoko.path.node;

/**
 * Created by edhendil on 19.07.17.
 */
public class StaticNode implements PathNode {

    private final String name;

    StaticNode(String name) {
        if (name.contains("/")) {
            throw new IllegalArgumentException("Static node cannot contain a slash character");
        }
        this.name = name;
    }

    @Override
    public boolean doesMatch(String value) {
        return value.equals(name);
    }

}
