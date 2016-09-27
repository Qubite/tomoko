package io.qubite.tomoko.handler.add;

import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.type.ValueType;

public interface AddHandler<T> {

    ValueType<T> getParameterClass();

    void execute(PathParameters pathParameters, T parameter);

}
