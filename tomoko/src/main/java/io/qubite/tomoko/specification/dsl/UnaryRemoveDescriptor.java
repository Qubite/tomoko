package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;

/**
 * Created by edhendil on 29.08.16.
 */
public class UnaryRemoveDescriptor<A> {

    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<?> pathTemplate;

    UnaryRemoveDescriptor(PathTemplate<A> firstArgumentPath, PathTemplate<?> pathTemplate) {
        this.firstArgumentPath = firstArgumentPath;
        this.pathTemplate = pathTemplate;
    }

    public Path createPath(A firstParameter) {
        PathTemplateParameters parameters = PathTemplateParameters.empty();
        parameters.addParameter(firstArgumentPath, firstParameter);
        return pathTemplate.generatePath(parameters);
    }

}
