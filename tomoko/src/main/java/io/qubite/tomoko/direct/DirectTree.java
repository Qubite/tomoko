package io.qubite.tomoko.direct;

import io.qubite.tomoko.patch.ValueTree;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DirectTree implements ValueTree {

    private final Map<String, DirectTree> children;
    private boolean valueSet;
    private Object value;

    DirectTree(Map<String, DirectTree> children, boolean valueSet, Object value) {
        this.children = children;
        this.valueSet = valueSet;
        this.value = value;
    }

    public static DirectTreeBuilder builder() {
        return new DirectTreeBuilder(DirectTree.empty());
    }

    public static <V> DirectTree of(V value) {
        return builder().setValue("", value).build();
    }

    static DirectTree empty() {
        return new DirectTree(new HashMap<>(), false, null);
    }

    public Map<String, DirectTree> getChildren() {
        return children;
    }

    @Override
    public Iterator<Map.Entry<String, ValueTree>> getFieldIterator() {
        return new ChildIterator(children.entrySet().iterator());
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public boolean isSingleChild() {
        return children.size() == 1;
    }

    public String getSingleChildName() {
        return children.keySet().iterator().next();
    }

    public DirectTree getChild(String name) {
        if (!children.containsKey(name)) {
            throw new IllegalArgumentException("No child of that name found");
        }
        return children.get(name);
    }

    public void addChild(String name, DirectTree node) {
        if (valueSet) {
            throw new IllegalStateException("Cannot addChild child. Value already set.");
        }
        children.put(name, node);
    }

    public void setValue(Object value) {
        if (!isLeaf()) {
            throw new IllegalStateException("Cannot set value on a non leaf node.");
        }
        this.value = value;
        this.valueSet = true;
    }

    public Object getValue() {
        if (!isLeaf()) {
            throw new IllegalStateException("Cannot get value from a non leaf node.");
        }
        return value;
    }

    public void addAll(Map<String, DirectTree> children) {
        this.children.putAll(children);
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return value.toString();
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (Map.Entry<String, DirectTree> entry : children.entrySet()) {
                builder.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append(", ");
            }
            if (children.entrySet().size() > 0) {
                builder.setLength(builder.length() - 2);
            }
            builder.append("}");
            return builder.toString();
        }
    }

    private static class ChildIterator implements Iterator<Map.Entry<String, ValueTree>> {

        private final Iterator<Map.Entry<String, DirectTree>> originalIterator;

        private ChildIterator(Iterator<Map.Entry<String, DirectTree>> originalIterator) {
            this.originalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return originalIterator.hasNext();
        }

        @Override
        public Map.Entry<String, ValueTree> next() {
            Map.Entry<String, DirectTree> next = originalIterator.next();
            return new AbstractMap.SimpleEntry<>(next.getKey(), next.getValue());
        }
    }

}
