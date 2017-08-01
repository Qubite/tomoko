package io.qubite.tomoko.direct.patch;

import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.path.Path;

import java.util.List;

public class PatchAddOperationBuilder {

    private final List<OperationDto> operations;
    private final AddOperationBuilder addBuilder;

    PatchAddOperationBuilder(List<OperationDto> operations, AddOperationBuilder addOperationBuilder) {
        this.operations = operations;
        this.addBuilder = addOperationBuilder;
    }

    static PatchAddOperationBuilder create(List<OperationDto> operations) {
        return new PatchAddOperationBuilder(operations, AddOperationBuilder.create());
    }

    public <V> PatchAddOperationBuilder add(Path path, V value) {
        addBuilder.add(path, value);
        return new PatchAddOperationBuilder(operations, addBuilder);
    }

    public <V> PatchAddOperationBuilder add(String path, V value) {
        return add(Path.parse(path), value);
    }

    public PatchBuilder toOperation() {
        operations.add(addBuilder.build());
        return new PatchBuilder(operations);
    }

}
