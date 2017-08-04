package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.ConfigurationException;
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

/**
 * Domain specification language for path specification phase. Allows adding new nodes and registering handlers on the current path.<br/>
 * <br/>
 * Path to be added to the current path must follow a Spring-like syntax.<br/>
 * "/users/{username}/comments/{commentId:[0-9]+}" is a valid example containing 4 nodes and 2 parameters.<br/>
 * "/users" and "/comments" are static nodes. They are to be matched exactly as defined.<br/>
 * "/{username}" is a wildcard parameter named "username". It will accept any non empty string.<br/>
 * "/{commentId:[0-9]+}" is a regex parameter named "commentId". It will accept any string matching the "[0-9]+" pattern.<br/>
 * <br/>
 */
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
     * @param parameterName
     * @return
     */
    public PathDSL wildcard(String parameterName) {
        return path(PatternElement.wildcardParameter(parameterName));
    }

    /**
     * Adds an integer parameter to the path. It will accept any string matching the "[0-9]+" pattern.
     *
     * @return
     */
    public PathDSL integer(String parameterName) {
        return path(PatternElement.parameter(parameterName, "^[0-9]+$"));
    }

    /**
     * Adds a string parameter restricted by a regular expression to the path.
     *
     * @param parameterName
     * @return
     */
    public PathDSL regex(String parameterName, String regex) {
        return path(PatternElement.parameter(parameterName, regex));
    }

    /**
     * Extends the current path by the provided string. The path must follow the syntax defined in the {@link PathDSL class description}.
     *
     * @param uriLikePath
     * @return
     */
    public PathDSL path(String uriLikePath) {
        PathPattern path;
        try {
            path = PathPattern.parse(uriLikePath);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Invalid path syntax", e);
        }
        return path(path);
    }

    /**
     * Adds the end index static token ("-") to the path. Usually marks the end of a path.
     *
     * @return
     */
    public PathDSL endIndex() {
        return path(PatternElement.fixed("-"));
    }

    /**
     * Sets the provided handler as a base of the ADD operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <V>     value type
     * @return ongoing handler configuration
     */
    public <V> NullaryValueHandlerSpec<V> handleAdd(Consumer<V> handler) {
        return NullaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the ADD operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <A, V> UnaryValueHandlerSpec<A, V> handleAdd(BiConsumer<A, V> handler) {
        return UnaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the ADD operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <B> second parameter type
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <A, B, V> BinaryValueHandlerSpec<A, B, V> handleAdd(TriConsumer<A, B, V> handler) {
        return BinaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the ADD operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <B> second parameter type
     * @param <C> third parameter type
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <A, B, C, V> TernaryValueHandlerSpec<A, B, C, V> handleAdd(QuadConsumer<A, B, C, V> handler) {
        return TernaryValueHandlerSpec.of(CommandType.ADD, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the REPLACE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <V> NullaryValueHandlerSpec<V> handleReplace(Consumer<V> handler) {
        return new NullaryValueHandlerSpec<>(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory, null);
    }

    /**
     * Sets the provided handler as a base of the REPLACE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <A, V> UnaryValueHandlerSpec<A, V> handleReplace(BiConsumer<A, V> handler) {
        return UnaryValueHandlerSpec.of(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the REPLACE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <B> second parameter type
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <A, B, V> BinaryValueHandlerSpec<A, B, V> handleReplace(TriConsumer<A, B, V> handler) {
        return BinaryValueHandlerSpec.of(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the REPLACE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <B> second parameter type
     * @param <C> third parameter type
     * @param <V> value type
     * @return ongoing handler configuration
     */
    public <A, B, C, V> TernaryValueHandlerSpec<A, B, C, V> handleReplace(QuadConsumer<A, B, C, V> handler) {
        return TernaryValueHandlerSpec.of(CommandType.REPLACE, pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the REMOVE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @return ongoing handler configuration
     */
    public NullaryValuelessHandlerDescriptor handleRemove(Runnable handler) {
        return NullaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory).register();
    }

    /**
     * Sets the provided handler as a base of the REMOVE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @return ongoing handler configuration
     */
    public <A> UnaryValuelessHandlerSpec<A> handleRemove(Consumer<A> handler) {
        return UnaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the REMOVE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <B> second parameter type
     * @return ongoing handler configuration
     */
    public <A, B> BinaryValuelessHandlerSpec<A, B> handleRemove(BiConsumer<A, B> handler) {
        return BinaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Sets the provided handler as a base of the REMOVE operation handler configuration.<br/>
     * Ends the path definition phase and starts the handler configuration one.
     *
     * @param handler code to be invoked when a matching patch operation is encountered
     * @param <A> first parameter type
     * @param <B> second parameter type
     * @param <C> third parameter type
     * @return ongoing handler configuration
     */
    public <A, B, C> TernaryValuelessHandlerSpec<A, B, C> handleRemove(TriConsumer<A, B, C> handler) {
        return TernaryValuelessHandlerSpec.of(pathPattern, handler, builder, handlerFactory);
    }

    private PathDSL path(PatternElement element) {
        return new PathDSL(pathPattern.append(element), builder, handlerFactory);
    }

    private PathDSL path(PathPattern path) {
        return new PathDSL(pathPattern.append(path), builder, handlerFactory);
    }

}
