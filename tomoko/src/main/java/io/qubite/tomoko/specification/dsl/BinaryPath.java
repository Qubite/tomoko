package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.path.node.ValueNode;
import io.qubite.tomoko.path.node.ValuelessNode;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.SimpleType;
import io.qubite.tomoko.type.Types;
import net.jodah.typetools.TypeResolver;

import java.util.function.BiConsumer;

/**
 * Created by edhendil on 28.08.16.
 */
public class BinaryPath<A, B> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<B> secondArgumentPath;
    private final PathTemplate<?> path;

    BinaryPath(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<B> secondArgumentPath, PathTemplate<?> path) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.secondArgumentPath = secondArgumentPath;
        this.path = path;
    }

    public BinaryPath<A, B> path(ValuelessNode node) {
        return new BinaryPath(builder, firstArgumentPath, secondArgumentPath, path.then(node));
    }

    public <C> TernaryPath<A, B, C> path(ValueNode<C> node) {
        PathTemplate<C> argPath = path.then(node);
        return new TernaryPath(builder, firstArgumentPath, secondArgumentPath, argPath, argPath);
    }

    public BinaryPath<A, B> text(String text) {
        return path(PathNodes.staticNode(text));
    }

    public TernaryPath<A, B, String> text() {
        return path(PathNodes.textNode());
    }

    public TernaryPath<A, B, Integer> integer() {
        return path(PathNodes.integerNode());
    }

    public TernaryPath<A, B, Integer> integer(int min, int max) {
        return path(PathNodes.integerNode(min, max));
    }

    public TernaryPath<A, B, Integer> minInteger(int min) {
        return path(PathNodes.minIntegerNode(min));
    }

    public TernaryPath<A, B, Integer> maxInteger(int max) {
        return path(PathNodes.maxIntegerNode(max));
    }

    public TernaryPath<A, B, Long> longValue() {
        return path(PathNodes.longNode());
    }

    public TernaryPath<A, B, String> regex(String regex) {
        return path(PathNodes.regexNode(regex));
    }

    public BinaryTypeSpec<A, B> endIndex() {
        return new BinaryTypeSpec(builder, firstArgumentPath, secondArgumentPath, path.then(PathNodes.endIndexNode()));
    }

    public BinaryTypeSpec<A, B> add() {
        return new BinaryTypeSpec(builder, firstArgumentPath, secondArgumentPath, path);
    }

    /**
     * Only use for simple types, not for generics i.e. collections, maps.
     *
     * @param consumer
     * @param <V>
     * @return
     */
    public <V> BinaryAddDescriptor<A, B, V> add(TriConsumer<A, B, V> consumer) {
        return add(path, consumer);
    }

    public <V> BinaryAddDescriptor<A, B, V> endIndex(TriConsumer<A, B, V> consumer) {
        return add(path.then(PathNodes.endIndexNode()), consumer);
    }

    public BinaryRemoveDescriptor<A, B> remove(BiConsumer<A, B> runnable) {
        builder.remove(path, firstArgumentPath, secondArgumentPath, runnable);
        return new BinaryRemoveDescriptor(firstArgumentPath, secondArgumentPath, path);
    }

    private <V> BinaryAddDescriptor<A, B, V> add(PathTemplate<?> path, TriConsumer<A, B, V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(TriConsumer.class, consumer.getClass());
        Class<V> valueClass = (Class<V>) classes[2];
        DslPreconditions.checkNotUnknown(valueClass);
        DslPreconditions.checkNonGeneric(valueClass);
        SimpleType<V> type = Types.simple(valueClass);
        builder.add(path, type, firstArgumentPath, secondArgumentPath, consumer);
        return new BinaryAddDescriptor(path, firstArgumentPath, secondArgumentPath, type);
    }

}
