package io.qubite.tomoko.handler;

/**
 * Created by edhendil on 29.08.16.
 */
public interface QuadConsumer<A, B, C, D> {

    void accept(A a, B b, C c, D d);

}
