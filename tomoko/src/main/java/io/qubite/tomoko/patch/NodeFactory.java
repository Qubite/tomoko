package io.qubite.tomoko.patch;

import java.io.InputStream;

public interface NodeFactory {

    ValueTree toTree(String json);

    ValueTree toTree(InputStream stream);

}
