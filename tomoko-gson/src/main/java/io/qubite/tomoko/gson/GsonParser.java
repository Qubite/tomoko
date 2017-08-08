package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import io.qubite.tomoko.handler.value.converter.ValueParser;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.type.*;

import java.lang.reflect.Type;

/**
 * Parser for GsonTree implementation of the ValueTree interface.
 */
public class GsonParser implements ValueParser<GsonTree> {

    private final Gson gson;

    GsonParser(Gson gson) {
        this.gson = gson;
    }

    public static GsonParser instance() {
        return instance(new Gson());
    }

    public static GsonParser instance(Gson gson) {
        return new GsonParser(gson);
    }

    @Override
    public <T> T getAs(GsonTree element, ValueType<T> valueType) {
        try {
            return parse(element.getValue(), valueType);
        } catch (JsonSyntaxException e) {
            throw new ConverterException("Could not parse the provided value as " + valueType, e);
        }
    }

    private <T> T parse(JsonElement element, ValueType<T> valueType) {
        if (valueType instanceof SimpleType) {
            SimpleType simpleType = (SimpleType) valueType;
            return (T) parse(element, simpleType.getBaseClass());
        } else if (valueType instanceof GenericType) {
            GenericType genericType = (GenericType) valueType;
            Type type = CustomParametrizedType.of(genericType.getBaseClass(), genericType.getParameterTypes());
            return (T) parse(element, type);
        } else if (valueType instanceof CollectionType) {
            CollectionType collectionType = (CollectionType) valueType;
            Type type = CustomParametrizedType.of(collectionType.getBaseClass(), collectionType.getElementClass());
            return (T) parse(element, type);
        } else if (valueType instanceof MapType) {
            MapType mapType = (MapType) valueType;
            Type type = CustomParametrizedType.of(mapType.getBaseClass(), mapType.getKeyClass(), mapType.getValueClass());
            return (T) parse(element, type);
        } else {
            throw new IllegalArgumentException("Unexpected class instance encountered: " + valueType.getClass());
        }
    }

    private Object parse(JsonElement element, Type type) {
        return gson.fromJson(element, type);
    }

    @Override
    public Class<GsonTree> supportedType() {
        return GsonTree.class;
    }

}
