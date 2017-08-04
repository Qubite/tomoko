package io.qubite.tomoko.patch;

import java.util.Iterator;
import java.util.Map;

/**
 * Patch operation value represented in the form of tree.
 */
public interface ValueTree {

    Iterator<Map.Entry<String, ValueTree>> getFieldIterator();

}
