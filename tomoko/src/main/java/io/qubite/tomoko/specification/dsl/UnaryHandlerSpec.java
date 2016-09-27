package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.ValueType;

import java.util.function.BiConsumer;

/**
 * Created by edhendil on 29.08.16.
 */
public class UnaryHandlerSpec<A, V> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<?> path;
    private final ValueType<V> valueType;

    UnaryHandlerSpec(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<?> path, ValueType<V> valueType) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.path = path;
        this.valueType = valueType;
    }

    public UnaryAddDescriptor<A, V> handle(BiConsumer<A, V> handler) {
        builder.add(path, valueType, firstArgumentPath, handler);
        return new UnaryAddDescriptor(path, firstArgumentPath, valueType);
    }

}
