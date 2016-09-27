package io.qubite.tomoko.tree.search;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by edhendil on 16.08.16.
 */
public class SearchContext<T, A> implements VisitorContext<T, A> {

    private final Queue<T> queue;
    private final A result;

    SearchContext(Queue<T> queue, A result) {
        this.queue = queue;
        this.result = result;
    }

    public static <T, A> SearchContext<T, A> of(T initialNode, A initialResult) {
        LinkedList<T> nodes = new LinkedList<>();
        nodes.add(initialNode);
        return new SearchContext(nodes, initialResult);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void addNode(T node) {
        queue.add(node);
    }

    @Override
    public A getResult() {
        return result;
    }

    public T poll() {
        return queue.poll();
    }

}
