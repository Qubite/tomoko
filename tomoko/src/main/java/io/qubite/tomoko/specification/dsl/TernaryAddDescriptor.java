package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;
import io.qubite.tomoko.type.ValueType;

/**
 * Created by edhendil on 28.08.16.
 */
public class TernaryAddDescriptor<A, B, C, V> {

    private final PathTemplate<?> path;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<C> thirdArgumentPath;
    private final ValueType<V> valueType;

    TernaryAddDescriptor(PathTemplate<?> path, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<C> thirdArgumentPath, ValueType<V> valueType) {
        this.path = path;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.thirdArgumentPath = thirdArgumentPath;
        this.valueType = valueType;
    }

    public Path createPath(A firstParameter, B secondParameter, C thirdParameter) {
        PathTemplateParameters parameters = PathTemplateParameters.empty();
        parameters.addParameter(firstArgumentPath, firstParameter);
        parameters.addParameter(secondArgumentPath, secondParameter);
        parameters.addParameter(thirdArgumentPath, thirdParameter);
        return path.generatePath(parameters);
    }

}
