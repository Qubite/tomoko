package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.specification.dsl.*;

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

    public PatchDSL remove(NullaryRemoveDescriptor descriptor) {
        Path path = descriptor.createPath();
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public <A> PatchDSL remove(UnaryRemoveDescriptor<A> descriptor, A firstParameter) {
        Path path = descriptor.createPath(firstParameter);
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public <A, B> PatchDSL remove(BinaryRemoveDescriptor<A, B> descriptor, A firstParameter, B secondParameter) {
        Path path = descriptor.createPath(firstParameter, secondParameter);
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public <A, B, C> PatchDSL remove(TernaryRemoveDescriptor<A, B, C> descriptor, A firstParameter, B secondParameter, C thirdParameter) {
        Path path = descriptor.createPath(firstParameter, secondParameter, thirdParameter);
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public <V> PatchDSL replace(NullaryReplaceDescriptor<V> descriptor, V value) {
        Path path = descriptor.createPath();
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    public <A, V> PatchDSL replace(UnaryReplaceDescriptor<A, V> descriptor, A firstParameter, V value) {
        Path path = descriptor.createPath(firstParameter);
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    public <A, B, V> PatchDSL replace(BinaryReplaceDescriptor<A, B, V> descriptor, A firstParameter, B secondParameter, V value) {
        Path path = descriptor.createPath(firstParameter, secondParameter);
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    public <A, B, C, V> PatchDSL replace(TernaryReplaceDescriptor<A, B, C, V> descriptor, A firstParameter, B secondParameter, C thirdParameter, V value) {
        Path path = descriptor.createPath(firstParameter, secondParameter, thirdParameter);
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    public <V> PatchAddDSL add(NullaryAddDescriptor<V> descriptor, V value) {
        return PatchAddDSL.create(operations).add(descriptor, value);
    }

    public <A, V> PatchAddDSL add(UnaryAddDescriptor<A, V> descriptor, A firstParameter, V value) {
        return PatchAddDSL.create(operations).add(descriptor, firstParameter, value);
    }

    public <A, B, V> PatchAddDSL add(BinaryAddDescriptor<A, B, V> descriptor, A firstParameter, B secondParameter, V value) {
        return PatchAddDSL.create(operations).add(descriptor, firstParameter, secondParameter, value);
    }

    public <A, B, C, V> PatchAddDSL add(TernaryAddDescriptor<A, B, C, V> descriptor, A firstParameter, B secondParameter, C thirdParameter, V value) {
        return PatchAddDSL.create(operations).add(descriptor, firstParameter, secondParameter, thirdParameter, value);
    }

    public Patch toPatch() {
        return Patch.of(operations);
    }

}
