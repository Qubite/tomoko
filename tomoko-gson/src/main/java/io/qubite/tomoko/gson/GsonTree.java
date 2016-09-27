package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.type.*;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by edhendil on 16.08.16.
 */
class GsonTree implements JsonTree {

    private final JsonElement element;
    private final Gson gson;

    GsonTree(JsonElement element, Gson gson) {
        this.element = element;
        this.gson = gson;
    }

    static GsonTree of(JsonElement element, Gson gson) {
        return new GsonTree(element, gson);
    }

    @Override
    public Iterator<Map.Entry<String, JsonTree>> getFieldIterator() {
        return new GsonFieldIterator(element.getAsJsonObject().entrySet().iterator());
    }

    @Override
    public <T> T getAs(ValueType<T> valueType) {
        try {
            return parse(valueType);
        } catch (JsonSyntaxException e) {
            throw new PatcherException("Value type mismatch between registered handler and received operation. Expected: " + valueType, e);
        }
    }

    private <T> T parse(ValueType<T> valueType) {
        if (valueType instanceof SimpleType) {
            SimpleType simpleType = (SimpleType) valueType;
            return (T) parse(simpleType.getBaseClass());
        } else if (valueType instanceof GenericType) {
            GenericType genericType = (GenericType) valueType;
            Type type = CustomParametrizedType.of(genericType.getBaseClass(), genericType.getParameterTypes());
            return (T) parse(type);
        } else if (valueType instanceof CollectionType) {
            CollectionType collectionType = (CollectionType) valueType;
            Type type = CustomParametrizedType.of(collectionType.getBaseClass(), collectionType.getElementClass());
            return (T) parse(type);
        } else if (valueType instanceof MapType) {
            MapType mapType = (MapType) valueType;
            Type type = CustomParametrizedType.of(mapType.getBaseClass(), mapType.getKeyClass(), mapType.getValueClass());
            return (T) parse(type);
        } else {
            throw new IllegalArgumentException("Unexpected class instance encountered: " + valueType.getClass());
        }
    }

    private Object parse(Type type) {
        return gson.fromJson(element, type);
    }

    private class GsonFieldIterator implements Iterator<Map.Entry<String, JsonTree>> {

        private final Iterator<Map.Entry<String, JsonElement>> originalIterator;

        private GsonFieldIterator(Iterator<Map.Entry<String, JsonElement>> originalIterator) {
            this.originalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return originalIterator.hasNext();
        }

        @Override
        public Map.Entry<String, JsonTree> next() {
            Map.Entry<String, JsonElement> next = originalIterator.next();
            return new AbstractMap.SimpleEntry(next.getKey(), GsonTree.of(next.getValue(), gson));
        }

    }

}
