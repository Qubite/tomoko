package io.qubite.tomoko.patch;

import io.qubite.tomoko.type.ValueType;

import java.util.Iterator;
import java.util.Map;

public interface ValueTree {

    Iterator<Map.Entry<String, ValueTree>> getFieldIterator();

    <T> T getAs(ValueType<T> valueType);

}
