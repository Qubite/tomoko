package io.qubite.tomoko.json;

import io.qubite.tomoko.type.ValueType;

import java.util.Iterator;
import java.util.Map;

public interface JsonTree {

    Iterator<Map.Entry<String, JsonTree>> getFieldIterator();

    <T> T getAs(ValueType<T> valueType);

}
