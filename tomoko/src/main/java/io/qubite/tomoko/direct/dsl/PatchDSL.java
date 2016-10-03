package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.specification.dsl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edhendil on 03.10.16.
 */
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

    public <A, B> PatchDSL remove(BinaryRemoveDescriptor descriptor, A firstParameter, B secondParameter) {
        Path path = descriptor.createPath(firstParameter, secondParameter);
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    public <A, B, C> PatchDSL remove(TernaryRemoveDescriptor descriptor, A firstParameter, B secondParameter, C thirdParameter) {
        Path path = descriptor.createPath(firstParameter, secondParameter, thirdParameter);
        operations.add(OperationDto.remove(path.toString()));
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
