package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNode;

import java.util.*;

/**
 * Created by edhendil on 30.08.16.
 */
public class TreeTextFormat {

    public String treeToString(TreeSpecification treeSpecification) {
        return addHandlersToString(treeSpecification) + removeHandlersToString(treeSpecification);
    }

    public String addHandlersToString(TreeSpecification treeSpecification) {
        StringBuilder builder = new StringBuilder();
        Tree<AddHandler<?>> addTree = treeSpecification.getAddHandlerTree();
        if (addTree.isLeaf() && !addTree.isHandlerRegistered()) {
            builder.append("No ADD handlers");
        } else {
            List<AddEntry> allHandlers = findAllAddHandlers((TreeNode<AddHandler<?>>) addTree);
            for (AddEntry entry : allHandlers) {
                builder.append(entry.toString());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public String removeHandlersToString(TreeSpecification treeSpecification) {
        StringBuilder builder = new StringBuilder();
        Tree<RemoveHandler> removeTree = treeSpecification.getRemoveHandlerTree();
        if (removeTree.isLeaf() && !removeTree.isHandlerRegistered()) {
            builder.append("No REMOVE handlers");
        } else {
            List<PathTemplate> allHandlers = findAllRemoveHandlers((TreeNode<RemoveHandler>) removeTree);
            for (PathTemplate path : allHandlers) {
                builder.append(path.toString());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private List<AddEntry> findAllAddHandlers(TreeNode<AddHandler<?>> addTreeRoot) {
        List<AddEntry> result = new ArrayList<>();
        Queue<AddStringRepresentationContext> queue = new LinkedList<>();
        AddStringRepresentationContext context = AddStringRepresentationContext.of(PathTemplate.empty(), addTreeRoot);
        queue.add(context);
        while (!queue.isEmpty()) {
            AddStringRepresentationContext current = queue.poll();
            if (current.getNode().isLeaf()) {
                result.add(new AddEntry(current.getPathTemplate(), current.getNode().getHandler()));
            } else {
                for (Map.Entry<PathNode, TreeNode<AddHandler<?>>> entry : current.getNode().getChildren().entrySet()) {
                    queue.add(current.with(entry.getKey(), entry.getValue()));
                }
            }
        }
        return result;
    }

    private List<PathTemplate> findAllRemoveHandlers(TreeNode<RemoveHandler> removeTreeRoot) {
        List<PathTemplate> result = new ArrayList<>();
        Queue<RemoveStringRepresentationContext> queue = new LinkedList<>();
        RemoveStringRepresentationContext context = RemoveStringRepresentationContext.of(PathTemplate.empty(), removeTreeRoot);
        queue.add(context);
        while (!queue.isEmpty()) {
            RemoveStringRepresentationContext current = queue.poll();
            if (current.getNode().isHandlerRegistered()) {
                result.add(current.getPathTemplate());
            }
            if (!current.getNode().isLeaf()) {
                for (Map.Entry<PathNode, TreeNode<RemoveHandler>> entry : current.getNode().getChildren().entrySet()) {
                    queue.add(current.with(entry.getKey(), entry.getValue()));
                }
            }
        }
        return result;
    }

    private static class AddEntry {

        private final PathTemplate pathTemplate;
        private final AddHandler<?> handler;

        private AddEntry(PathTemplate pathTemplate, AddHandler<?> handler) {
            this.pathTemplate = pathTemplate;
            this.handler = handler;
        }

        public PathTemplate getPathTemplate() {
            return pathTemplate;
        }

        public AddHandler<?> getHandler() {
            return handler;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s", pathTemplate, handler.getParameterClass());
        }

    }

}
