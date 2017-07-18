package io.qubite.tomoko.specification;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.Handlers;
import io.qubite.tomoko.handler.QuadConsumer;
import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.tree.TreeNode;
import io.qubite.tomoko.type.ValueType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by edhendil on 15.08.16.
 */
public class TreeSpecificationBuilder {

    private final TreeNode<ValueHandler<?>> addRoot;
    private final TreeNode<ValuelessHandler> removeRoot;
    private final TreeNode<ValueHandler<?>> replaceRoot;

    TreeSpecificationBuilder(TreeNode<ValueHandler<?>> addRoot, TreeNode<ValuelessHandler> removeRoot, TreeNode<ValueHandler<?>> replaceRoot) {
        this.addRoot = addRoot;
        this.removeRoot = removeRoot;
        this.replaceRoot = replaceRoot;
    }

    public <T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, Consumer<T> consumer) {
        ValueHandler<T> handler = Handlers.handler(parameterClass, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <A, T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, BiConsumer<A, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        ValueHandler<T> handler = Handlers.handler(parameterClass, firstParameterNode, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <A, B, T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        ValueHandler<T> handler = Handlers.handler(parameterClass, firstParameterNode, secondParameterNode, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <A, B, C, T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        isParameterPathASubpath(pathTemplate, thirdParameterNode);
        ValueHandler<T> handler = Handlers.handler(parameterClass, firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <T> TreeSpecificationBuilder handleReplace(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, Consumer<T> consumer) {
        ValueHandler<T> handler = Handlers.handler(parameterClass, consumer);
        return handleReplace(pathTemplate, handler);
    }

    public <A, T> TreeSpecificationBuilder handleReplace(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, BiConsumer<A, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        ValueHandler<T> handler = Handlers.handler(parameterClass, firstParameterNode, consumer);
        return handleReplace(pathTemplate, handler);
    }

    public <A, B, T> TreeSpecificationBuilder handleReplace(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        ValueHandler<T> handler = Handlers.handler(parameterClass, firstParameterNode, secondParameterNode, consumer);
        return handleReplace(pathTemplate, handler);
    }

    public <A, B, C, T> TreeSpecificationBuilder handleReplace(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        isParameterPathASubpath(pathTemplate, thirdParameterNode);
        ValueHandler<T> handler = Handlers.handler(parameterClass, firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
        return handleReplace(pathTemplate, handler);
    }

    public TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, Runnable runnable) {
        ValuelessHandler handler = Handlers.handler(runnable);
        return handleRemove(pathTemplate, handler);
    }

    public <A> TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, PathTemplate<A> firstParameterNode, Consumer<A> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        ValuelessHandler handler = Handlers.handler(firstParameterNode, consumer);
        return handleRemove(pathTemplate, handler);
    }

    public <A, B> TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, BiConsumer<A, B> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        ValuelessHandler handler = Handlers.handler(firstParameterNode, secondParameterNode, consumer);
        return handleRemove(pathTemplate, handler);
    }

    public <A, B, C> TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        isParameterPathASubpath(pathTemplate, thirdParameterNode);
        ValuelessHandler handler = Handlers.handler(firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
        return handleRemove(pathTemplate, handler);
    }

    public TreeSpecification build() {
        return new TreeSpecification(addRoot, removeRoot, replaceRoot);
    }

    private void isParameterPathASubpath(PathTemplate handlerPath, PathTemplate parameterPath) {
        if (!handlerPath.isSubpath(parameterPath)) {
            throw new PatcherException("Provided parameter node is not a part of this path.");
        }
    }

    private <T> TreeSpecificationBuilder handleAdd(PathTemplate pathTemplate, ValueHandler<T> handler) {
        TreeNode<ValueHandler<?>> node = addRoot.extendUntilHandler(pathTemplate);
        if (!node.isLeaf()) {
            throw new PatcherException("Add operation handler must be registered on a leaf node.");
        }
        node.setHandler(handler);
        return this;
    }

    private TreeSpecificationBuilder handleRemove(PathTemplate pathTemplate, ValuelessHandler handler) {
        TreeNode<ValuelessHandler> node = removeRoot.extend(pathTemplate);
        node.setHandler(handler);
        return this;
    }

    private <T> TreeSpecificationBuilder handleReplace(PathTemplate pathTemplate, ValueHandler<T> handler) {
        TreeNode<ValueHandler<?>> node = replaceRoot.extend(pathTemplate);
        node.setHandler(handler);
        return this;
    }

}
