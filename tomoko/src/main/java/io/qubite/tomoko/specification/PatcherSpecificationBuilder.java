package io.qubite.tomoko.specification;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.tree.IllegalPathExtensionException;
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
        TreeNode<ValueHandler> node;
        try {
            node = addRoot.extendUntilHandler(pathTemplate);
        } catch (IllegalPathExtensionException e) {
            throw new ConfigurationException("A handler has already been registered as a ancestor of the requested path.");
        }
        checkLeaf(node);
        checkNoHandler(node);
        node.setHandler(handler);
        return this;
    }

    public PatcherSpecificationBuilder handleRemove(PathTemplate pathTemplate, ValuelessHandler handler) {
        TreeNode<ValuelessHandler> node = removeRoot.extend(pathTemplate);
        checkNoHandler(node);
        node.setHandler(handler);
        return this;
    }

    public PatcherSpecificationBuilder handleReplace(PathTemplate pathTemplate, ValueHandler handler) {
        TreeNode<ValueHandler> node = replaceRoot.extend(pathTemplate);
        checkNoHandler(node);
        node.setHandler(handler);
        return this;
    }

    public PatcherSpecification build() {
        return new PatcherSpecification(addRoot, removeRoot, replaceRoot);
    }

    private void checkNoHandler(TreeNode<?> node) {
        if (node.isHandlerRegistered()) {
            throw new ConfigurationException("Handler is already registered on this path.");
        }
    }

    private void checkLeaf(TreeNode<?> node) {
        if (!node.isLeaf()) {
            throw new ConfigurationException("A handler has already been registered as a descendant of the requested path.");
        }
    }

}
