package io.qubite.tomoko.specification;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNodes;

/**
 * Created by edhendil on 17.08.16.
 */
public class TreeSpecification {

    private final Tree<AddHandler<?>> addHandlerTree;
    private final Tree<RemoveHandler> removeHandlerTree;

    TreeSpecification(Tree<AddHandler<?>> addHandlerTree, Tree<RemoveHandler> removeHandlerTree) {
        this.addHandlerTree = addHandlerTree;
        this.removeHandlerTree = removeHandlerTree;
    }

    public static TreeSpecificationBuilder builder() {
        return new TreeSpecificationBuilder(TreeNodes.root(), TreeNodes.root());
    }

    public Tree<AddHandler<?>> getAddHandlerTree() {
        return addHandlerTree;
    }

    public Tree<RemoveHandler> getRemoveHandlerTree() {
        return removeHandlerTree;
    }

}
