package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNode;

import java.util.*;

public class TreeTextFormat {

    public String treeToString(PatcherSpecification patcherSpecification) {
        String handlerDescription = addHandlersToString(patcherSpecification) + removeHandlersToString(patcherSpecification) + replaceHandlersToString(patcherSpecification);
        return handlerDescription.isEmpty() ? "No handlers registered" : "List of registered handlers\n" + handlerDescription;
    }

    public String addHandlersToString(PatcherSpecification patcherSpecification) {
        StringBuilder builder = new StringBuilder();
        Tree<ValueHandler> addTree = patcherSpecification.getAddHandlerTree();
        List<HandlerEntry<ValueHandler>> allHandlers = findAllHandlers((TreeNode<ValueHandler>) addTree);
        for (HandlerEntry<ValueHandler> entry : allHandlers) {
            builder.append("ADD ");
            builder.append(entry.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public String removeHandlersToString(PatcherSpecification patcherSpecification) {
        StringBuilder builder = new StringBuilder();
        Tree<ValuelessHandler> removeTree = patcherSpecification.getRemoveHandlerTree();
        List<HandlerEntry<ValuelessHandler>> allHandlers = findAllHandlers((TreeNode<ValuelessHandler>) removeTree);
        for (HandlerEntry<ValuelessHandler> entry : allHandlers) {
            builder.append("REMOVE ");
            builder.append(entry.getPathTemplate().toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public String replaceHandlersToString(PatcherSpecification patcherSpecification) {
        StringBuilder builder = new StringBuilder();
        Tree<ValueHandler> replaceTree = patcherSpecification.getReplaceHandlerTree();
        List<HandlerEntry<ValueHandler>> allHandlers = findAllHandlers((TreeNode<ValueHandler>) replaceTree);
        for (HandlerEntry<ValueHandler> entry : allHandlers) {
            builder.append("REPLACE ");
            builder.append(entry.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    private <T> List<HandlerEntry<T>> findAllHandlers(TreeNode<T> handlerRoot) {
        List<HandlerEntry<T>> result = new ArrayList<>();
        Queue<StringRepresentationContext<T>> queue = new LinkedList<>();
        StringRepresentationContext<T> context = StringRepresentationContext.of(PathTemplate.empty(), handlerRoot);
        queue.add(context);
        while (!queue.isEmpty()) {
            StringRepresentationContext<T> current = queue.poll();
            if (current.getNode().isHandlerRegistered()) {
                result.add(new HandlerEntry(current.getPathTemplate(), current.getNode().getHandler()));
            }
            if (!current.getNode().isLeaf()) {
                for (Map.Entry<PathNode, TreeNode<T>> entry : current.getNode().getChildren().entrySet()) {
                    queue.add(current.with(entry.getKey(), entry.getValue()));
                }
            }
        }
        return result;
    }

    private static class HandlerEntry<T> {

        private final PathTemplate pathTemplate;
        private final T handler;

        private HandlerEntry(PathTemplate pathTemplate, T handler) {
            this.pathTemplate = pathTemplate;
            this.handler = handler;
        }

        public PathTemplate getPathTemplate() {
            return pathTemplate;
        }

        public T getHandler() {
            return handler;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s", pathTemplate, handler);
        }

    }

}
