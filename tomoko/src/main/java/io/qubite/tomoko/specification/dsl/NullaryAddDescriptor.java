package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.PathTemplateParameters;
import io.qubite.tomoko.type.ValueType;

/**
 * Created by edhendil on 28.08.16.
 */
public class NullaryAddDescriptor<V> {

    private final PathTemplate<?> path;
    private final ValueType<V> valueType;

    NullaryAddDescriptor(PathTemplate<?> path, ValueType<V> valueType) {
        this.path = path;
        this.valueType = valueType;
    }

    public PathTemplate<?> getPath() {
        return path;
    }

    public ValueType<V> getValueType() {
        return valueType;
    }

    public Path createPath() {
        return path.generatePath(PathTemplateParameters.empty());
    }

}
