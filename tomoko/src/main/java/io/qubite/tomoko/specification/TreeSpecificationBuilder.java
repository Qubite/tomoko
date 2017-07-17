package io.qubite.tomoko.specification;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.Handlers;
import io.qubite.tomoko.handler.QuadConsumer;
import io.qubite.tomoko.handler.TriConsumer;
import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.tree.TreeNode;
import io.qubite.tomoko.type.ValueType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by edhendil on 15.08.16.
 */
public class TreeSpecificationBuilder {

    private final TreeNode<AddHandler<?>> addRoot;
    private final TreeNode<RemoveHandler> removeRoot;

    TreeSpecificationBuilder(TreeNode<AddHandler<?>> addRoot, TreeNode<RemoveHandler> removeRoot) {
        this.addRoot = addRoot;
        this.removeRoot = removeRoot;
    }

    public <T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, Consumer<T> consumer) {
        AddHandler<T> handler = Handlers.add(parameterClass, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <A, T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, BiConsumer<A, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        AddHandler<T> handler = Handlers.add(parameterClass, firstParameterNode, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <A, B, T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, TriConsumer<A, B, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        AddHandler<T> handler = Handlers.add(parameterClass, firstParameterNode, secondParameterNode, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public <A, B, C, T> TreeSpecificationBuilder handleAdd(PathTemplate<?> pathTemplate, ValueType<T> parameterClass, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, QuadConsumer<A, B, C, T> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        isParameterPathASubpath(pathTemplate, thirdParameterNode);
        AddHandler<T> handler = Handlers.add(parameterClass, firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
        return handleAdd(pathTemplate, handler);
    }

    public TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, Runnable runnable) {
        RemoveHandler handler = Handlers.remove(runnable);
        return handleRemove(pathTemplate, handler);
    }

    public <A> TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, PathTemplate<A> firstParameterNode, Consumer<A> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        RemoveHandler handler = Handlers.remove(firstParameterNode, consumer);
        return handleRemove(pathTemplate, handler);
    }

    public <A, B> TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, BiConsumer<A, B> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        RemoveHandler handler = Handlers.remove(firstParameterNode, secondParameterNode, consumer);
        return handleRemove(pathTemplate, handler);
    }

    public <A, B, C> TreeSpecificationBuilder handleRemove(PathTemplate<?> pathTemplate, PathTemplate<A> firstParameterNode, PathTemplate<B> secondParameterNode, PathTemplate<C> thirdParameterNode, TriConsumer<A, B, C> consumer) {
        isParameterPathASubpath(pathTemplate, firstParameterNode);
        isParameterPathASubpath(pathTemplate, secondParameterNode);
        isParameterPathASubpath(pathTemplate, thirdParameterNode);
        RemoveHandler handler = Handlers.remove(firstParameterNode, secondParameterNode, thirdParameterNode, consumer);
        return handleRemove(pathTemplate, handler);
    }

    public TreeSpecification build() {
        return new TreeSpecification(addRoot, removeRoot);
    }

    private void isParameterPathASubpath(PathTemplate handlerPath, PathTemplate parameterPath) {
        if (!handlerPath.isSubpath(parameterPath)) {
            throw new PatcherException("Provided parameter node is not a part of this path.");
        }
    }

    private <T> TreeSpecificationBuilder handleAdd(PathTemplate pathTemplate, AddHandler<T> handler) {
        TreeNode<AddHandler<?>> node = addRoot.extendUntilHandler(pathTemplate);
        if (!node.isLeaf()) {
            throw new PatcherException("Add operation handler must be registered on a leaf node.");
        }
        node.setHandler(handler);
        return this;
    }

    private TreeSpecificationBuilder handleRemove(PathTemplate pathTemplate, RemoveHandler handler) {
        TreeNode<RemoveHandler> node = removeRoot.extend(pathTemplate);
        node.setHandler(handler);
        return this;
    }

}
