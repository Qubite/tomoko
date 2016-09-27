package io.qubite.tomoko.direct;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.type.ValueType;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by edhendil on 28.08.16.
 */
public class DirectTree implements JsonTree {

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

    static DirectTree empty() {
        return new DirectTree(new HashMap<>(), false, null);
    }

    public Map<String, DirectTree> getChildren() {
        return children;
    }

    @Override
    public Iterator<Map.Entry<String, JsonTree>> getFieldIterator() {
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

    @Override
    public <T> T getAs(ValueType<T> valueType) {
        if (!isLeaf()) {
            throw new PatcherException("No value present");
        }
        if (value == null) {
            return null;
        }
        if (!valueType.getBaseClass().isInstance(value)) {
            throw new PatcherException("Value valueType mismatch between registered handler and received operation. Expected: " + valueType);
        }
        return (T) value;
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

    public void addAll(Map<String, DirectTree> children) {
        this.children.putAll(children);
    }

    private static class ChildIterator implements Iterator<Map.Entry<String, JsonTree>> {

        private final Iterator<Map.Entry<String, DirectTree>> originalIterator;

        private ChildIterator(Iterator<Map.Entry<String, DirectTree>> originalIterator) {
            this.originalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return originalIterator.hasNext();
        }

        @Override
        public Map.Entry<String, JsonTree> next() {
            Map.Entry<String, DirectTree> next = originalIterator.next();
            return new AbstractMap.SimpleEntry<>(next.getKey(), next.getValue());
        }
    }

}
