package io.qubite.tomoko.patch;

import java.util.Iterator;
import java.util.Map;

public interface ValueTree {

    Iterator<Map.Entry<String, ValueTree>> getFieldIterator();

}
