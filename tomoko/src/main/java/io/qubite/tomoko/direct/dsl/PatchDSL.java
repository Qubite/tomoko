package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;

import java.util.ArrayList;
import java.util.List;

public class PatchDSL {

    private final List<OperationDto> operations;

    PatchDSL(List<OperationDto> operations) {
        this.operations = operations;
    }

    public static PatchDSL dsl() {
        return new PatchDSL(new ArrayList<>());
    }

    public PatchDSL remove(Path path) {
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public <V> PatchDSL replace(Path path, V value) {
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    public <V> PatchAddDSL add(Path path, V value) {
        return PatchAddDSL.create(operations).add(path, value);
    }

    public Patch toPatch() {
        return Patch.of(operations);
    }

}
