package io.qubite.tomoko.json;

import java.io.InputStream;

/**
 * Created by edhendil on 19.08.16.
 */
public interface NodeFactory {

    JsonTree toTree(String json);

    JsonTree toTree(InputStream stream);

}
