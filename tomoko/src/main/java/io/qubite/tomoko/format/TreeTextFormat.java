package io.qubite.tomoko.format;

import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeIterator;

public class TreeTextFormat {

    public String treeToString(PatcherTreeSpecification patcherTreeSpecification) {
        String handlerDescription = addHandlersToString(patcherTreeSpecification) + removeHandlersToString(patcherTreeSpecification) + replaceHandlersToString(patcherTreeSpecification);
        return handlerDescription.isEmpty() ? "No handlers registered" : "List of registered handlers\n" + handlerDescription;
    }

    public String addHandlersToString(PatcherTreeSpecification patcherTreeSpecification) {
        return handlerTreeToString(patcherTreeSpecification.getAddHandlerTree(), "ADD");
    }

    public String removeHandlersToString(PatcherTreeSpecification patcherTreeSpecification) {
        return handlerTreeToString(patcherTreeSpecification.getRemoveHandlerTree(), "REMOVE");
    }

    public String replaceHandlersToString(PatcherTreeSpecification patcherTreeSpecification) {
        return handlerTreeToString(patcherTreeSpecification.getReplaceHandlerTree(), "REPLACE");
    }

    private <T> String handlerTreeToString(Tree<T> tree, String type) {
        StringBuilder builder = new StringBuilder();
        for (TreeIterator.TreeEntry<T> entry : tree) {
            if (entry.getTree().isHandlerRegistered()) {
                builder.append(type + " ");
                builder.append(String.format("%s -> %s", entry.getPathTemplate(), entry.getTree().getHandler()));
                builder.append("\n");
            }
        }
        return builder.toString();
    }

}
