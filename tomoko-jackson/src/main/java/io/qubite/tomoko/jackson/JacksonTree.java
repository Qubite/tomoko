package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.type.*;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by edhendil on 16.08.16.
 */
class JacksonTree implements JsonTree {

    private final JsonNode node;
    private final ObjectMapper mapper;

    JacksonTree(JsonNode node, ObjectMapper mapper) {
        this.node = node;
        this.mapper = mapper;
    }

    static JacksonTree of(JsonNode node, ObjectMapper mapper) {
        return new JacksonTree(node, mapper);
    }

    public <T> T getAs(ValueType<T> valueType) {
        try {
            return parse(valueType);
        } catch (JsonMappingException e) {
            throw new PatcherException("Value valueType mismatch between registered handler and received operation. Expected: " + valueType, e);
        } catch (IOException e) {
            throw new PatcherException(e);
        }
    }

    public Iterator<Map.Entry<String, JsonTree>> getFieldIterator() {
        return new JacksonIterator(node.fields());
    }

    @Override
    public String toString() {
        return node.toString();
    }

    private <T> T parse(ValueType<T> valueType) throws IOException {
        if (valueType instanceof SimpleType) {
            SimpleType simpleType = (SimpleType) valueType;
            JavaType type = TypeFactory.defaultInstance().uncheckedSimpleType(simpleType.getBaseClass());
            return (T) parse(type);
        } else if (valueType instanceof GenericType) {
            GenericType genericType = (GenericType) valueType;
            JavaType javaType = TypeFactory.defaultInstance().constructParametricType(genericType.getBaseClass(), genericType.getParameterTypes());
            return (T) parse(javaType);
        } else if (valueType instanceof CollectionType) {
            CollectionType collectionType = (CollectionType) valueType;
            JavaType javaType = TypeFactory.defaultInstance().constructCollectionType(collectionType.getBaseClass(), collectionType.getElementClass());
            return (T) parse(javaType);
        } else if (valueType instanceof MapType) {
            MapType mapType = (MapType) valueType;
            JavaType javaType = TypeFactory.defaultInstance().constructMapType(mapType.getBaseClass(), mapType.getKeyClass(), mapType.getValueClass());
            return (T) parse(javaType);
        } else {
            throw new IllegalArgumentException("Unexpected class instance encountered: " + valueType.getClass());
        }
    }

    private Object parse(JavaType javaType) throws IOException {
        return mapper.readValue(node.traverse(), javaType);
    }

    private class JacksonIterator implements Iterator<Map.Entry<String, JsonTree>> {

        private final Iterator<Map.Entry<String, JsonNode>> originalIterator;

        private JacksonIterator(Iterator<Map.Entry<String, JsonNode>> originalIterator) {
            this.originalIterator = originalIterator;
        }

        @Override
        public boolean hasNext() {
            return originalIterator.hasNext();
        }

        @Override
        public Map.Entry<String, JsonTree> next() {
            Map.Entry<String, JsonNode> next = originalIterator.next();
            return new AbstractMap.SimpleEntry(next.getKey(), JacksonTree.of(next.getValue(), mapper));
        }
    }

}
