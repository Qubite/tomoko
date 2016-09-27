package io.qubite.tomoko.json;

import io.qubite.tomoko.type.ValueType;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by edhendil on 16.08.16.
 */
public interface JsonTree {

    Iterator<Map.Entry<String, JsonTree>> getFieldIterator();

    <T> T getAs(ValueType<T> valueType);

}
