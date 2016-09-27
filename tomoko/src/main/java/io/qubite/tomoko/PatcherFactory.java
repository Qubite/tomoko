package io.qubite.tomoko;

import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.TreeSpecification;

/**
 * Created by edhendil on 29.08.16.
 */
public class PatcherFactory {

    public static PatcherFactory instance() {
        return new PatcherFactory();
    }

    public Patcher create(TreeSpecification patchSpecification) {
        return new PatcherImpl(patchSpecification, new OperationExecutorImpl(new HandlerResolver()));
    }

}