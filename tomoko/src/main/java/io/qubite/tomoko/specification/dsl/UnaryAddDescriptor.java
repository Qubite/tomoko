package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;
import io.qubite.tomoko.type.ValueType;

/**
 * Created by edhendil on 28.08.16.
 */
public class UnaryAddDescriptor<A, V> {

    private final PathTemplate<?> path;
    private final PathTemplate<A> firstArgumentPath;
    private final ValueType<V> valueType;

    UnaryAddDescriptor(PathTemplate<?> path, PathTemplate<A> firstArgumentPath, ValueType<V> valueType) {
        this.path = path;
        this.firstArgumentPath = firstArgumentPath;
        this.valueType = valueType;
    }

    public Path createPath(A firstParameter) {
        PathTemplateParameters parameters = PathTemplateParameters.empty();
        parameters.addParameter(firstArgumentPath, firstParameter);
        return path.generatePath(parameters);
    }

}
