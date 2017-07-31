package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.path.Path;

import java.util.List;

public class PatchAddDSL {

    private final List<OperationDto> operations;
    private final AddOperationBuilder addBuilder;

    PatchAddDSL(List<OperationDto> operations, AddOperationBuilder addOperationBuilder) {
        this.operations = operations;
        this.addBuilder = addOperationBuilder;
    }

    static PatchAddDSL create(List<OperationDto> operations) {
        return new PatchAddDSL(operations, AddOperationBuilder.create());
    }

    public <V> PatchAddDSL add(Path path, V value) {
        addBuilder.add(path, value);
        return new PatchAddDSL(operations, addBuilder);
    }

    public <V> PatchAddDSL add(String path, V value) {
        return add(Path.parse(path), value);
    }

    public PatchDSL toOperation() {
        operations.add(addBuilder.build());
        return new PatchDSL(operations);
    }

}
