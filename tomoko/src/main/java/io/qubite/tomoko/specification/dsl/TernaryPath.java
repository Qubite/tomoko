package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.QuadConsumer;
import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.path.node.ValuelessNode;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.SimpleType;
import io.qubite.tomoko.type.Types;
import net.jodah.typetools.TypeResolver;

/**
 * Created by edhendil on 29.08.16.
 */
public class TernaryPath<A, B, C> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<C> thirdArgumentPath;
    private final PathTemplate<?> path;

    TernaryPath(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<C> thirdArgumentPath, PathTemplate<?> path) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.thirdArgumentPath = thirdArgumentPath;
        this.path = path;
    }

    public TernaryPath<A, B, C> path(ValuelessNode node) {
        return new TernaryPath(builder, firstArgumentPath, secondArgumentPath, thirdArgumentPath, path.then(node));
    }

    public TernaryPath<A, B, C> text(String text) {
        return path(PathNodes.staticNode(text));
    }

    public TernaryTypeSpec<A, B, C> endIndex() {
        return new TernaryTypeSpec(builder, firstArgumentPath, secondArgumentPath, thirdArgumentPath, path.then(PathNodes.endIndexNode()));
    }

    public TernaryTypeSpec<A, B, C> add() {
        return new TernaryTypeSpec(builder, firstArgumentPath, secondArgumentPath, thirdArgumentPath, path);
    }

    /**
     * Only use for simple types, not for generics i.e. collections, maps.
     *
     * @param consumer
     * @param <V>
     * @return
     */
    public <V> TernaryAddDescriptor<A, B, C, V> add(QuadConsumer<A, B, C, V> consumer) {
        return add(path, consumer);
    }

    public <V> TernaryAddDescriptor<A, B, C, V> endIndex(QuadConsumer<A, B, C, V> consumer) {
        return add(path.then(PathNodes.endIndexNode()), consumer);
    }

    public TernaryRemoveDescriptor<A, B, C> remove(TriConsumer<A, B, C> runnable) {
        builder.remove(path, firstArgumentPath, secondArgumentPath, thirdArgumentPath, runnable);
        return new TernaryRemoveDescriptor(firstArgumentPath, secondArgumentPath, thirdArgumentPath, path);
    }

    private <V> TernaryAddDescriptor<A, B, C, V> add(PathTemplate<?> path, QuadConsumer<A, B, C, V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(QuadConsumer.class, consumer.getClass());
        Class<V> valueClass = (Class<V>) classes[3];
        DslPreconditions.checkNotUnknown(valueClass);
        DslPreconditions.checkNonGeneric(valueClass);
        SimpleType<V> type = Types.simple(valueClass);
        builder.add(path, type, firstArgumentPath, secondArgumentPath, thirdArgumentPath, consumer);
        return new TernaryAddDescriptor(path, firstArgumentPath, secondArgumentPath, thirdArgumentPath, type);
    }

}
