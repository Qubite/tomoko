package io.qubite.tomoko.tree.search;

/**
 * Created by edhendil on 16.08.16.
 */
public class TreeSearch {

    public static <T, A> A deepSearch(T initialNode, A initialResult, NodeVisitor<T, A> visitor) {
        SearchContext<T, A> context = SearchContext.of(initialNode, initialResult);
        while (!context.isEmpty()) {
            T current = context.poll();
            visitor.visit(current, context);
        }
        return context.getResult();
    }

}
