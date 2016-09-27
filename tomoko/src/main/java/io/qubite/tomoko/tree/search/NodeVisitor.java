package io.qubite.tomoko.tree.search;

/**
 * Created by edhendil on 16.08.16.
 */
@FunctionalInterface
public interface NodeVisitor<T, A> {

    void visit(T node, VisitorContext<T, A> context);

}
