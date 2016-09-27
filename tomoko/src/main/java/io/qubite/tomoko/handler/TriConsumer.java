package io.qubite.tomoko.handler;

/**
 * Created by edhendil on 11.08.16.
 */
public interface TriConsumer<A, B, C> {

    void accept(A a, B b, C c);

}
