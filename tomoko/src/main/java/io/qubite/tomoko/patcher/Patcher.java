package io.qubite.tomoko.patcher;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;

/**
 * <p>
 * Patcher consumes a patch and invokes corresponding handlers.
 * </p>
 * <p>
 * Throws {@link io.qubite.tomoko.resolver.HandlerNotFoundException} if there is no handler registered for the provided operation.
 * </p>
 * <p>
 * Throws {@link io.qubite.tomoko.handler.HandlerException} if a handler was found but there was a problem with its execution.
 * </p>
 */
public interface Patcher {

    /**
     * Execute a single patch operation against the underlying handler configuration.
     *
     * @param operation
     */
    void execute(OperationDto operation);

    /**
     * Execute a set of patch operations against the underlying handler configuration.
     *
     * @param operations
     */
    void execute(Patch operations);

}
