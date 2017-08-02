package io.qubite.tomoko.direct.patch;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;

import java.util.ArrayList;
import java.util.List;

public class PatchBuilder {

    private final List<OperationDto> operations;

    PatchBuilder(List<OperationDto> operations) {
        this.operations = operations;
    }

    public static PatchBuilder instance() {
        return new PatchBuilder(new ArrayList<>());
    }

    public PatchBuilder remove(Path path) {
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public PatchBuilder remove(String path) {
        operations.add(OperationDto.remove(path));
        return this;
    }

    public <V> PatchBuilder replace(Path path, V value) {
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    public <V> PatchBuilder replace(String path, V value) {
        operations.add(OperationDto.replace(path, DirectTree.of(value)));
        return this;
    }

    public PatchBuilder add(String path, DirectTree directTree) {
        operations.add(OperationDto.add(path, directTree));
        return this;
    }

    public <V> PatchAddOperationBuilder add(Path path, V value) {
        return PatchAddOperationBuilder.create(operations).add(path, value);
    }

    public <V> PatchAddOperationBuilder add(String path, V value) {
        return PatchAddOperationBuilder.create(operations).add(path, value);
    }

    public Patch toPatch() {
        return Patch.of(operations);
    }

}
