package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.specification.dsl.BinaryAddDescriptor;
import io.qubite.tomoko.specification.dsl.NullaryAddDescriptor;
import io.qubite.tomoko.specification.dsl.TernaryAddDescriptor;
import io.qubite.tomoko.specification.dsl.UnaryAddDescriptor;

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

    public <V> PatchAddDSL add(NullaryAddDescriptor<V> descriptor, V value) {
        addBuilder.add(descriptor, value);
        return new PatchAddDSL(operations, addBuilder);
    }

    public <A, V> PatchAddDSL add(UnaryAddDescriptor<A, V> descriptor, A firstParameter, V value) {
        addBuilder.add(descriptor, firstParameter, value);
        return new PatchAddDSL(operations, addBuilder);
    }

    public <A, B, V> PatchAddDSL add(BinaryAddDescriptor<A, B, V> descriptor, A firstParameter, B secondParameter, V value) {
        addBuilder.add(descriptor, firstParameter, secondParameter, value);
        return new PatchAddDSL(operations, addBuilder);
    }

    public <A, B, C, V> PatchAddDSL add(TernaryAddDescriptor<A, B, C, V> descriptor, A firstParameter, B secondParameter, C thirdParameter, V value) {
        addBuilder.add(descriptor, firstParameter, secondParameter, thirdParameter, value);
        return new PatchAddDSL(operations, addBuilder);
    }

    public PatchDSL toOperation() {
        operations.add(addBuilder.build());
        return new PatchDSL(operations);
    }

}
