package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;

/**
 * Created by edhendil on 29.08.16.
 */
public class BinaryRemoveDescriptor<A, B> {

    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<?> pathTemplate;

    BinaryRemoveDescriptor(PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<?> pathTemplate) {
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.pathTemplate = pathTemplate;
    }

    public Path createPath(A firstParameter, B secondParameter) {
        PathTemplateParameters parameters = PathTemplateParameters.empty();
        parameters.addParameter(firstArgumentPath, firstParameter);
        parameters.addParameter(secondArgumentPath, secondParameter);
        return pathTemplate.generatePath(parameters);
    }

}
