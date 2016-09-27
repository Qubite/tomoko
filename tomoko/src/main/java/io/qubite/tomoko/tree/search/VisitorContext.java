package io.qubite.tomoko.tree.search;

/**
 * Created by edhendil on 16.08.16.
 */
public interface VisitorContext<T, A> {

    void addNode(T node);

    A getResult();

}


