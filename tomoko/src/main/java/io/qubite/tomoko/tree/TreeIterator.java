package io.qubite.tomoko.tree;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TreeIterator<T> implements Iterator<TreeIterator.TreeEntry<T>> {

    private final Queue<TreeEntry<T>> queue;

    TreeIterator(Queue<TreeEntry<T>> queue) {
        this.queue = queue;
    }

    public static <T> TreeIterator<T> instance(Tree<T> tree) {
        Queue<TreeEntry<T>> queue = new LinkedList<>();
        queue.add(TreeEntry.instance(PathTemplate.empty(), tree));
        return new TreeIterator<>(queue);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public TreeEntry next() {
        TreeEntry<T> currentItem = queue.poll();
        for (Map.Entry<PathNode, ? extends Tree<T>> entry : currentItem.getTree().getChildren().entrySet()) {
            queue.add(currentItem.with(entry.getKey(), entry.getValue()));
        }
        return currentItem;
    }

    public static class TreeEntry<T> {

        private final PathTemplate pathTemplate;
        private final Tree<T> tree;

        TreeEntry(PathTemplate pathTemplate, Tree<T> tree) {
            this.pathTemplate = pathTemplate;
            this.tree = tree;
        }

        static <T> TreeEntry<T> instance(PathTemplate pathTemplate, Tree<T> tree) {
            return new TreeEntry<>(pathTemplate, tree);
        }

        public TreeEntry<T> with(PathNode node, Tree<T> tree) {
            return new TreeEntry<>(pathTemplate.append(node), tree);
        }

        public PathTemplate getPathTemplate() {
            return pathTemplate;
        }

        public Tree<T> getTree() {
            return tree;
        }

    }

}
