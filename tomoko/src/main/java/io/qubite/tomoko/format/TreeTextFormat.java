package io.qubite.tomoko.format;

import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNode;
import io.qubite.tomoko.tree.search.TreeSearch;
import io.qubite.tomoko.tree.search.VisitorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        AddStringRepresentationContext context = AddStringRepresentationContext.of(PathTemplate.empty(), addTreeRoot);
        List<AddEntry> result = new ArrayList<>();
        TreeSearch.deepSearch(context, result, this::visitAddNode);
        return result;
    }

    private List<PathTemplate> findAllRemoveHandlers(TreeNode<RemoveHandler> removeTreeRoot) {
        RemoveStringRepresentationContext context = RemoveStringRepresentationContext.of(PathTemplate.empty(), removeTreeRoot);
        List<PathTemplate> result = new ArrayList<>();
        TreeSearch.deepSearch(context, result, this::visitRemoveNode);
        return result;
    }

    private void visitAddNode(AddStringRepresentationContext current, VisitorContext<AddStringRepresentationContext, List<AddEntry>> context) {
        if (current.getNode().isLeaf()) {
            context.getResult().add(new AddEntry(current.getPathTemplate(), current.getNode().getHandler()));
        } else {
            for (Map.Entry<PathNode, TreeNode<AddHandler<?>>> entry : current.getNode().getChildren().entrySet()) {
                context.addNode(current.with(entry.getKey(), entry.getValue()));
            }
        }
    }

    private void visitRemoveNode(RemoveStringRepresentationContext current, VisitorContext<RemoveStringRepresentationContext, List<PathTemplate>> context) {
        if (current.getNode().isHandlerRegistered()) {
            context.getResult().add(current.getPathTemplate());
        }
        if (!current.getNode().isLeaf()) {
            for (Map.Entry<PathNode, TreeNode<RemoveHandler>> entry : current.getNode().getChildren().entrySet()) {
                context.addNode(current.with(entry.getKey(), entry.getValue()));
            }
        }
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
