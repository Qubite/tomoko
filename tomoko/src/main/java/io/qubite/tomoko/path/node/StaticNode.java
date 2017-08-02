package io.qubite.tomoko.path.node;

import io.qubite.tomoko.util.Preconditions;

import java.util.Objects;

public class StaticNode implements PathNode {

    private final String name;

    StaticNode(String name) {
        Preconditions.checkNotNull(name);
        if (name.contains("/")) {
            throw new IllegalArgumentException("Static node cannot contain a slash character");
        }
        this.name = name;
    }

    @Override
    public boolean doesMatch(String value) {
        return name.equals(value);
    }

    @Override
    public String toString() {
        return "/" + name;
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
        return Objects.hashCode(name);
    }
}
