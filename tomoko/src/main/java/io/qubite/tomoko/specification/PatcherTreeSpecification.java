package io.qubite.tomoko.specification;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNodes;

/**
 * <p>
 * Handler tree model.
 * </p>
 * <p>
 * Contains trees of registered paths and handlers for ADD, REMOVE and REPLACE operations.
 * </p>
 * Immutable.
 */
public class PatcherTreeSpecification {

    private final Tree<ValueHandler> addHandlerTree;
    private final Tree<ValuelessHandler> removeHandlerTree;
    private final Tree<ValueHandler> replaceHandlerTree;

    PatcherTreeSpecification(Tree<ValueHandler> addHandlerTree, Tree<ValuelessHandler> removeHandlerTree, Tree<ValueHandler> replaceHandlerTree) {
        this.addHandlerTree = addHandlerTree;
        this.removeHandlerTree = removeHandlerTree;
        this.replaceHandlerTree = replaceHandlerTree;
    }

    public static PatcherTreeSpecificationBuilder builder() {
        return new PatcherTreeSpecificationBuilder(TreeNodes.root(), TreeNodes.root(), TreeNodes.root());
    }

    public Tree<ValueHandler> getAddHandlerTree() {
        return addHandlerTree;
    }

    public Tree<ValuelessHandler> getRemoveHandlerTree() {
        return removeHandlerTree;
    }

    public Tree<ValueHandler> getReplaceHandlerTree() {
        return replaceHandlerTree;
    }
}
