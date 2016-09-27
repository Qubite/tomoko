package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.QuadConsumer;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.ValueType;

/**
 * Created by edhendil on 29.08.16.
 */
public class TernaryHandlerSpec<A, B, C, V> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<C> thirdArgumentPath;
    private final PathTemplate<?> path;
    private final ValueType<V> valueType;

    TernaryHandlerSpec(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<C> thirdArgumentPath, PathTemplate<?> path, ValueType<V> valueType) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.thirdArgumentPath = thirdArgumentPath;
        this.path = path;
        this.valueType = valueType;
    }

    public TernaryAddDescriptor<A, B, C, V> handle(QuadConsumer<A, B, C, V> handler) {
        builder.add(path, valueType, firstArgumentPath, secondArgumentPath, thirdArgumentPath, handler);
        return new TernaryAddDescriptor(path, firstArgumentPath, secondArgumentPath, thirdArgumentPath, valueType);
    }

}
