package io.qubite.tomoko.operation;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.resolver.HandlerResolver;

public interface OperationExecutor {

    void execute(HandlerResolver handlerResolver, OperationDto operation);

    void execute(HandlerResolver handlerResolver, Patch operations);

}
