package io.qubite.tomoko.handler;

public interface TriConsumer<A, B, C> {

    void accept(A a, B b, C c);

}
