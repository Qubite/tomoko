package io.qubite.tomoko.specification;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNodes;

public class TreeSpecification {

    private final Tree<ValueHandler<?>> addHandlerTree;
    private final Tree<ValuelessHandler> removeHandlerTree;
    private final Tree<ValueHandler<?>> replaceHandlerTree;

    TreeSpecification(Tree<ValueHandler<?>> addHandlerTree, Tree<ValuelessHandler> removeHandlerTree, Tree<ValueHandler<?>> replaceHandlerTree) {
        this.addHandlerTree = addHandlerTree;
        this.removeHandlerTree = removeHandlerTree;
        this.replaceHandlerTree = replaceHandlerTree;
    }

    public static TreeSpecificationBuilder builder() {
        return new TreeSpecificationBuilder(TreeNodes.root(), TreeNodes.root(), TreeNodes.root());
    }

    public Tree<ValueHandler<?>> getAddHandlerTree() {
        return addHandlerTree;
    }

    public Tree<ValuelessHandler> getRemoveHandlerTree() {
        return removeHandlerTree;
    }

    public Tree<ValueHandler<?>> getReplaceHandlerTree() {
        return replaceHandlerTree;
    }
}
