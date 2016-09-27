package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;
import io.qubite.tomoko.type.ValueType;

/**
 * Created by edhendil on 28.08.16.
 */
public class BinaryAddDescriptor<A, B, V> {

    private final PathTemplate<?> path;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final ValueType<V> valueType;

    BinaryAddDescriptor(PathTemplate<?> path, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, ValueType<V> valueType) {
        this.path = path;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.valueType = valueType;
    }

    public Path createPath(A firstParameter, B secondParameter) {
        PathTemplateParameters parameters = PathTemplateParameters.empty();
        parameters.addParameter(firstArgumentPath, firstParameter);
        parameters.addParameter(secondArgumentPath, secondParameter);
        return path.generatePath(parameters);
    }

}
