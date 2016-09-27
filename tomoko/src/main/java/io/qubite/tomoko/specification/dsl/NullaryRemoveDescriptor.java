package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;

/**
 * Created by edhendil on 29.08.16.
 */
public class NullaryRemoveDescriptor {

    private final PathTemplate<?> path;

    NullaryRemoveDescriptor(PathTemplate<?> path) {
        this.path = path;
    }

    public Path createPath() {
        return path.generatePath(PathTemplateParameters.empty());
    }

}
