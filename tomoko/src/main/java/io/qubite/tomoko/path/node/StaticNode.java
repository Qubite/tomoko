package io.qubite.tomoko.path.node;

import java.util.Objects;

/**
 * Created by edhendil on 10.08.16.
 */
public class StaticNode implements ValuelessNode {

    private final String name;

    StaticNode(String name) {
        this.name = name;
    }

    @Override
    public boolean doesMatch(String value) {
        return name.equals(value);
    }

    public String toPathString(Void value) {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticNode)) {
            return false;
        }
        StaticNode that = (StaticNode) o;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "/" + name;
    }
}
