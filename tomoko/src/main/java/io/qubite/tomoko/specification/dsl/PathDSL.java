package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.valueless.NullaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.specification.scanner.PatternElement;
import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PathDSL {

    private final PathPattern pathPattern;
    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    PathDSL(PathPattern pathPattern, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        this.pathPattern = pathPattern;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
    }

    public static PathDSL empty(PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new PathDSL(PathPattern.empty(), builder, handlerFactory);
    }

    /**
     * Adds a string parameter to the path.
     *
     * @return
     */
    public PathDSL wildcard(String parameterName) {
        return node(PatternElement.wildcardParameter(parameterName));
    }

    /**
     * Adds an integer parameter to the path.
     *
     * @return
     */
    public PathDSL integer(String parameterName) {
        return node(PatternElement.parameter(parameterName, "^[0-9]+$"));
    }

    /**
     * Adds a string parameter restricted by a regular expression to the path.
     *
     * @return
     */
    public PathDSL regex(String parameterName, String regex) {
        return node(PatternElement.parameter(parameterName, regex));
    }

    /**
     * Adds a static string to the path e.g. "author/firstName".<br/><br/>
     * Splits the parameter into separate tokens by the "/" character and adds each token as a separate node.
     * The "/" character is prohibited at the start and at the end of the provided parameter.
     *
     * @param text
     * @return
     */
    public PathDSL node(String text) {
        String[] nodes = text.split("/", -1);
        PathDSL result = this;
        for (String node : nodes) {
            if (node.isEmpty()) {
                throw new IllegalArgumentException("Path node name cannot be empty");
            }
            result = node(PatternElement.fixed(node));
        }
        return result;
    }

    /**
     * Adds the end index static token ("-") to the path.
     *
     * @return
     */
    public PathDSL endIndex() {
        return node(PatternElement.fixed("-"));
    }

    public <V> NullaryValueHandlerSpec<V> handleAdd(Consumer<V> handler) {
        return NullaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    public <A, V> UnaryValueHandlerSpec<A, V> handleAdd(BiConsumer<A, V> handler) {
        return UnaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    public <A, B, V> BinaryValueHandlerSpec<A, B, V> handleAdd(TriConsumer<A, B, V> handler) {
        return BinaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    public <A, B, C, V> TernaryValueHandlerSpec<A, B, C, V> handleAdd(QuadConsumer<A, B, C, V> handler) {
        return TernaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    public <V> NullaryValueHandlerSpec<V> handleReplace(Consumer<V> handler) {
        return new NullaryValueHandlerSpec<>(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory, null);
    }

    public <A, V> UnaryValueHandlerSpec<A, V> handleReplace(BiConsumer<A, V> handler) {
        return UnaryValueHandlerSpec.of(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory);
    }

    public <A, B, V> BinaryValueHandlerSpec<A, B, V> handleReplace(TriConsumer<A, B, V> handler) {
        return BinaryValueHandlerSpec.of(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory);
    }

    public <A, B, C, V> TernaryValueHandlerSpec<A, B, C, V> handleReplace(QuadConsumer<A, B, C, V> handler) {
        return TernaryValueHandlerSpec.of(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory);
    }

    public NullaryValuelessHandlerDescriptor handleRemove(Runnable handler) {
        return NullaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory).register();
    }

    public <A> UnaryValuelessHandlerSpec<A> handleRemove(Consumer<A> handler) {
        return UnaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory);
    }

    public <A, B> BinaryValuelessHandlerSpec<A, B> handleRemove(BiConsumer<A, B> handler) {
        return BinaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory);
    }

    public <A, B, C> TernaryValuelessHandlerSpec<A, B, C> handleRemove(TriConsumer<A, B, C> handler) {
        return TernaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory);
    }

    private PathDSL node(PatternElement element) {
        return new PathDSL(pathPattern.append(element), builder, handlerFactory);
    }

}
