package io.qubite.tomoko.handler.value;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.type.ValueType;

public interface ValueHandler<T> {

    ValueType<T> getParameterClass();

    void execute(PathParameters pathParameters, T parameter);

}
