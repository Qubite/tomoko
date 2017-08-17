package io.qubite.tomoko.resolver;

import io.qubite.tomoko.operation.ValueOperation;
import io.qubite.tomoko.operation.ValuelessOperation;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;

import java.util.List;

public interface HandlerResolver {

    List<ValueOperation> findAddHandlers(Path path, ValueTree value);

    ValueOperation findReplaceHandler(Path path, ValueTree value);

    ValuelessOperation findRemoveHandler(Path path);

}
