package io.qubite.tomoko;

import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.PatcherSpecification;

public class PatcherFactory {

    public static PatcherFactory instance() {
        return new PatcherFactory();
    }

    public Patcher create(PatcherSpecification patchSpecification) {
        return new PatcherImpl(patchSpecification, new OperationExecutorImpl(new HandlerResolver()));
    }

}
