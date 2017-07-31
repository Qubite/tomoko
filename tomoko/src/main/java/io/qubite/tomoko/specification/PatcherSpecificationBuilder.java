package io.qubite.tomoko.specification;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.tree.TreeNode;

public class PatcherSpecificationBuilder {

    private final TreeNode<ValueHandler> addRoot;
    private final TreeNode<ValuelessHandler> removeRoot;
    private final TreeNode<ValueHandler> replaceRoot;

    PatcherSpecificationBuilder(TreeNode<ValueHandler> addRoot, TreeNode<ValuelessHandler> removeRoot, TreeNode<ValueHandler> replaceRoot) {
        this.addRoot = addRoot;
        this.removeRoot = removeRoot;
        this.replaceRoot = replaceRoot;
    }

    public PatcherSpecificationBuilder handleAdd(PathTemplate pathTemplate, ValueHandler handler) {
        TreeNode<ValueHandler> node = addRoot.extendUntilHandler(pathTemplate);
        if (!node.isLeaf()) {
            throw new PatcherException("Add operation handler must be registered on a leaf node.");
        }
        node.setHandler(handler);
        return this;
    }

    public PatcherSpecificationBuilder handleRemove(PathTemplate pathTemplate, ValuelessHandler handler) {
        TreeNode<ValuelessHandler> node = removeRoot.extend(pathTemplate);
        node.setHandler(handler);
        return this;
    }

    public PatcherSpecificationBuilder handleReplace(PathTemplate pathTemplate, ValueHandler handler) {
        TreeNode<ValueHandler> node = replaceRoot.extend(pathTemplate);
        node.setHandler(handler);
        return this;
    }

    public PatcherSpecification build() {
        return new PatcherSpecification(addRoot, removeRoot, replaceRoot);
    }

}
