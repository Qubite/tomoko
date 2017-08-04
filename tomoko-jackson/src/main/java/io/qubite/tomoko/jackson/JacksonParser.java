package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.qubite.tomoko.handler.value.converter.ValueParser;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.type.*;

import java.io.IOException;

/**
 * Parser for JacksonTree implementation of the ValueTree interface.
 */
public class JacksonParser implements ValueParser<JacksonTree> {

    private final ObjectMapper mapper;

    JacksonParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static JacksonParser instance() {
        return instance(new ObjectMapper());
    }

    public static JacksonParser instance(ObjectMapper mapper) {
        return new JacksonParser(mapper);
    }

    public <T> T getAs(JacksonTree node, ValueType<T> valueType) {
        try {
            return parse(node.getValue(), valueType);
        } catch (JsonMappingException e) {
            throw new ConverterException("Could not parse the provided value as " + valueType);
        } catch (IOException e) {
            throw new ConverterException(e);
        }
    }

    @Override
    public Class<JacksonTree> supportedType() {
        return JacksonTree.class;
    }

    private <T> T parse(JsonNode node, ValueType<T> valueType) throws IOException {
        JavaType javaType = null;
        if (valueType instanceof SimpleType) {
            SimpleType simpleType = (SimpleType) valueType;
            javaType = TypeFactory.defaultInstance().uncheckedSimpleType(simpleType.getBaseClass());
        } else if (valueType instanceof GenericType) {
            GenericType genericType = (GenericType) valueType;
            javaType = TypeFactory.defaultInstance().constructParametricType(genericType.getBaseClass(), genericType.getParameterTypes());
        } else if (valueType instanceof CollectionType) {
            CollectionType collectionType = (CollectionType) valueType;
            javaType = TypeFactory.defaultInstance().constructCollectionType(collectionType.getBaseClass(), collectionType.getElementClass());
        } else if (valueType instanceof MapType) {
            MapType mapType = (MapType) valueType;
            javaType = TypeFactory.defaultInstance().constructMapType(mapType.getBaseClass(), mapType.getKeyClass(), mapType.getValueClass());
        } else {
            throw new IllegalArgumentException("Unexpected class instance encountered: " + valueType.getClass());
        }
        return (T) parse(node, javaType);
    }

    private Object parse(JsonNode node, JavaType javaType) throws IOException {
        return mapper.readValue(node.traverse(), javaType);
    }

}
