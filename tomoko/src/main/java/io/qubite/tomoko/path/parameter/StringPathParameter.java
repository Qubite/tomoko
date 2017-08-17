package io.qubite.tomoko.path.parameter;

import io.qubite.tomoko.path.Path;

public class StringPathParameter implements PathParameter<String> {

    private final int parameterIndex;

    StringPathParameter(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }

    public static StringPathParameter of(int parameterIndex) {
        return new StringPathParameter(parameterIndex);
    }

    @Override
    public String extractValue(Path path) {
        return path.getValue(parameterIndex);
    }

}
