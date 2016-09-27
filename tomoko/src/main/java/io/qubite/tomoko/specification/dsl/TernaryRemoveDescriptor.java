package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;

/**
 * Created by edhendil on 29.08.16.
 */
public class TernaryRemoveDescriptor<A, B, C> {

    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<C> thirdArgumentPath;
    private final PathTemplate<?> pathTemplate;

    TernaryRemoveDescriptor(PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<C> thirdArgumentPath, PathTemplate<?> pathTemplate) {
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.thirdArgumentPath = thirdArgumentPath;
        this.pathTemplate = pathTemplate;
    }

    public Path createPath(A firstParameter, B secondParameter, C thirdParameter) {
        PathTemplateParameters parameters = PathTemplateParameters.empty();
        parameters.addParameter(firstArgumentPath, firstParameter);
        parameters.addParameter(secondArgumentPath, secondParameter);
        parameters.addParameter(thirdArgumentPath, thirdParameter);
        return pathTemplate.generatePath(parameters);
    }

}
