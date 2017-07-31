package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.patch.ValueTree;

public interface ValueConverter<T> {

    T parse(ValueTree valueTree);

}
