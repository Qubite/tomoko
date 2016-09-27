package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.path.node.ValueNode;
import io.qubite.tomoko.path.node.ValuelessNode;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.SimpleType;
import io.qubite.tomoko.type.Types;
import net.jodah.typetools.TypeResolver;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by edhendil on 28.08.16.
 */
public class UnaryPath<A> {

    private final TreeSpecificationBuilder builder;
    private final PathTemplate<A> firstArgumentPath;
    private final PathTemplate<?> path;

    UnaryPath(TreeSpecificationBuilder builder, PathTemplate<A> firstArgumentPath, PathTemplate<?> path) {
        this.builder = builder;
        this.firstArgumentPath = firstArgumentPath;
        this.path = path;
    }

    public UnaryPath<A> path(ValuelessNode node) {
        return new UnaryPath(builder, firstArgumentPath, path.then(node));
    }

    public <B> BinaryPath<A, B> path(ValueNode<B> node) {
        PathTemplate<B> argPath = path.then(node);
        return new BinaryPath(builder, firstArgumentPath, argPath, argPath);
    }

    public UnaryPath<A> text(String text) {
        return path(PathNodes.staticNode(text));
    }

    public BinaryPath<A, String> text() {
        return path(PathNodes.textNode());
    }

    public BinaryPath<A, Integer> integer() {
        return path(PathNodes.integerNode());
    }

    public BinaryPath<A, Integer> integer(int min, int max) {
        return path(PathNodes.integerNode(min, max));
    }

    public BinaryPath<A, Integer> minInteger(int min) {
        return path(PathNodes.minIntegerNode(min));
    }

    public BinaryPath<A, Integer> maxInteger(int max) {
        return path(PathNodes.maxIntegerNode(max));
    }

    public BinaryPath<A, Long> longValue() {
        return path(PathNodes.longNode());
    }

    public BinaryPath<A, String> regex(String regex) {
        return path(PathNodes.regexNode(regex));
    }

    public UnaryTypeSpec<A> endIndex() {
        return new UnaryTypeSpec(builder, firstArgumentPath, path.then(PathNodes.endIndexNode()));
    }

    public UnaryTypeSpec<A> add() {
        return new UnaryTypeSpec(builder, firstArgumentPath, path);
    }

    /**
     * Only use for simple types, not for generics i.e. collections, maps.
     *
     * @param consumer
     * @param <V>
     * @return
     */
    public <V> UnaryAddDescriptor<A, V> add(BiConsumer<A, V> consumer) {
        return add(path, consumer);
    }

    public <V> UnaryAddDescriptor<A, V> endIndex(BiConsumer<A, V> consumer) {
        return add(path.then(PathNodes.endIndexNode()), consumer);
    }

    public UnaryRemoveDescriptor<A> remove(Consumer<A> runnable) {
        builder.remove(path, firstArgumentPath, runnable);
        return new UnaryRemoveDescriptor(firstArgumentPath, path);
    }

    private <V> UnaryAddDescriptor<A, V> add(PathTemplate<?> path, BiConsumer<A, V> consumer) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(BiConsumer.class, consumer.getClass());
        Class<V> valueClass = (Class<V>) classes[1];
        DslPreconditions.checkNotUnknown(valueClass);
        DslPreconditions.checkNonGeneric(valueClass);
        SimpleType<V> type = Types.simple(valueClass);
        builder.add(path, type, firstArgumentPath, consumer);
        return new UnaryAddDescriptor(path, firstArgumentPath, type);
    }

}
