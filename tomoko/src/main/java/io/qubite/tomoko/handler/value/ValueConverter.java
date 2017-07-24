package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.patch.ValueTree;

/**
 * Created by edhendil on 19.07.17.
 */
public interface ValueConverter<T> {

    T parse(ValueTree valueTree);

}
