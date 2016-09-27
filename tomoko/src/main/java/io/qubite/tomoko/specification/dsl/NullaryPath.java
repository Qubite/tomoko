package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.path.node.ValueNode;
import io.qubite.tomoko.path.node.ValuelessNode;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.SimpleType;
import io.qubite.tomoko.type.Types;
import net.jodah.typetools.TypeResolver;

import java.util.function.Consumer;

/**
 * Created by edhendil on 28.08.16.
 */
public class NullaryPath {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<?> path;

    NullaryPath(TreeSpecificationBuilder builder, PathTemplate<?> path) {
        this.builder = builder;
        this.path = path;
    }

    public NullaryPath path(ValuelessNode node) {
        return new NullaryPath(builder, path.then(node));
    }

    public <A> UnaryPath<A> path(ValueNode<A> node) {
        PathTemplate<A> argPath = path.then(node);
        return new UnaryPath(builder, argPath, argPath);
    }

    public NullaryPath text(String text) {
        return path(PathNodes.staticNode(text));
    }

    public UnaryPath<String> text() {
        return path(PathNodes.textNode());
    }

    public UnaryPath<Integer> integer() {
        return path(PathNodes.integerNode());
    }

    public UnaryPath<Integer> integer(int min, int max) {
        return path(PathNodes.integerNode(min, max));
    }

    public UnaryPath<Integer> minInteger(int min) {
        return path(PathNodes.minIntegerNode(min));
    }

    public UnaryPath<Integer> maxInteger(int max) {
        return path(PathNodes.maxIntegerNode(max));
    }

    public UnaryPath<Long> longValue() {
        return path(PathNodes.longNode());
    }

    public UnaryPath<String> regex(String regex) {
        return path(PathNodes.regexNode(regex));
    }

    public NullaryTypeSpec endIndex() {
        return new NullaryTypeSpec(builder, path.then(PathNodes.endIndexNode()));
    }

    public NullaryTypeSpec add() {
        return new NullaryTypeSpec(builder, path);
    }

    /**
     * Only use for simple types, not for generics i.e. collections, maps.
     *
     * @param consumer
     * @param <V>
     * @return
     */
    public <V> NullaryAddDescriptor<V> add(Consumer<V> consumer) {
        return add(path, consumer);
    }

    public <V> NullaryAddDescriptor<V> endIndex(Consumer<V> consumer) {
        return add(path.then(PathNodes.endIndexNode()), consumer);
    }

    public NullaryRemoveDescriptor remove(Runnable runnable) {
        builder.remove(path, runnable);
        return new NullaryRemoveDescriptor(path);
    }

    private <V> NullaryAddDescriptor<V> add(PathTemplate<?> path, Consumer<V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(Consumer.class, consumer.getClass());
        Class<V> valueClass = (Class<V>) classes[0];
        DslPreconditions.checkNotUnknown(valueClass);
        DslPreconditions.checkNonGeneric(valueClass);
        SimpleType<V> type = Types.simple(valueClass);
        builder.add(path, type, consumer);
        return new NullaryAddDescriptor(path, type);
    }

}
