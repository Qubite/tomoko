package io.qubite.tomoko.json;

import java.io.InputStream;

public interface NodeFactory {

    JsonTree toTree(String json);

    JsonTree toTree(InputStream stream);

}
