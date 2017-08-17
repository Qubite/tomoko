package io.qubite.tomoko.path.parameter;

import io.qubite.tomoko.path.Path;

public interface PathParameter<T> {

    T extractValue(Path path);

}
