package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.ValueType;

import java.util.function.Consumer;

/**
 * Created by edhendil on 29.08.16.
 */
public class NullaryHandlerSpec<V> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<?> path;
    private final ValueType<V> valueType;

    NullaryHandlerSpec(TreeSpecificationBuilder builder, PathTemplate<?> path, ValueType<V> valueType) {
        this.builder = builder;
        this.path = path;
        this.valueType = valueType;
    }

    public NullaryAddDescriptor<V> handle(Consumer<V> handler) {
        builder.add(path, valueType, handler);
        return new NullaryAddDescriptor<V>(path, valueType);
    }

}
