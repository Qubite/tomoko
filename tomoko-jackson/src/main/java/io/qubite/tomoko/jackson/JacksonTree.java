package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import io.qubite.tomoko.patch.ValueTree;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

class JacksonTree implements ValueTree {

    private final JsonNode node;

    JacksonTree(JsonNode node) {
        this.node = node;
    }

    static JacksonTree of(JsonNode node) {
        return new JacksonTree(node);
    }

    public Iterator<Map.Entry<String, ValueTree>> getFieldIterator() {
        return new JacksonIterator(node.fields());
    }

    public JsonNode getValue() {
        return node;
    }

    @Override
    public String toString() {
        return node.toString();
    }

    private class JacksonIterator implements Iterator<Map.Entry<String, ValueTree>> {

        private final Iterator<Map.Entry<String, JsonNode>> originalIterator;

        private JacksonIterator(Iterator<Map.Entry<String, JsonNode>> originalIterator) {
            this.originalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return originalIterator.hasNext();
        }

        @Override
        public Map.Entry<String, ValueTree> next() {
            Map.Entry<String, JsonNode> next = originalIterator.next();
            return new AbstractMap.SimpleEntry(next.getKey(), JacksonTree.of(next.getValue()));
        }
    }

}
