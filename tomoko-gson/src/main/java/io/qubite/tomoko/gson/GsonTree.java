package io.qubite.tomoko.gson;

import com.google.gson.JsonElement;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.util.Preconditions;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

class GsonTree implements ValueTree {

    private final JsonElement element;

    GsonTree(JsonElement element) {
        this.element = element;
    }

    static GsonTree of(JsonElement element) {
        Preconditions.checkNotNull(element);
        return new GsonTree(element);
    }

    @Override
    public Iterator<Map.Entry<String, ValueTree>> getFieldIterator() {
        return new GsonFieldIterator(element.getAsJsonObject().entrySet().iterator());
    }

    public JsonElement getValue() {
        return element;
    }

    private class GsonFieldIterator implements Iterator<Map.Entry<String, ValueTree>> {

        private final Iterator<Map.Entry<String, JsonElement>> originalIterator;

        private GsonFieldIterator(Iterator<Map.Entry<String, JsonElement>> originalIterator) {
            this.originalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return originalIterator.hasNext();
        }

        @Override
        public Map.Entry<String, ValueTree> next() {
            Map.Entry<String, JsonElement> next = originalIterator.next();
            return new AbstractMap.SimpleEntry(next.getKey(), GsonTree.of(next.getValue()));
        }

    }

}
