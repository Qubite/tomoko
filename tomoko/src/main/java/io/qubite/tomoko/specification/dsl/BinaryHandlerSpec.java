package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.ValueType;

/**
 * Created by edhendil on 29.08.16.
 */
public class BinaryHandlerSpec<A, B, V> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<?> path;
    private final ValueType<V> valueType;

    BinaryHandlerSpec(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<?> path, ValueType<V> valueType) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.path = path;
        this.valueType = valueType;
    }

    public BinaryAddDescriptor<A, B, V> handle(TriConsumer<A, B, V> handler) {
        builder.add(path, valueType, firstArgumentPath, secondArgumentPath, handler);
        return new BinaryAddDescriptor(path, firstArgumentPath, secondArgumentPath, valueType);
    }

}
