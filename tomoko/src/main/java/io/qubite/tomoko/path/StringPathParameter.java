package io.qubite.tomoko.path;

/**
 * Created by edhendil on 19.07.17.
 */
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
