package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.direct.DirectTreeBuilder;
import io.qubite.tomoko.json.CommandType;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.specification.dsl.BinaryAddDescriptor;
import io.qubite.tomoko.specification.dsl.NullaryAddDescriptor;
import io.qubite.tomoko.specification.dsl.TernaryAddDescriptor;
import io.qubite.tomoko.specification.dsl.UnaryAddDescriptor;

/**
 * Created by edhendil on 30.08.16.
 */
public class AddOperationBuilder {

    private final DirectTreeBuilder builder;

    AddOperationBuilder(DirectTreeBuilder builder) {
        this.builder = builder;
    }

    public static AddOperationBuilder create() {
        return new AddOperationBuilder(DirectTree.builder());
    }

    public <V> AddOperationBuilder add(NullaryAddDescriptor<V> descriptor, V value) {
        Path path = descriptor.createPath();
        builder.setValue(path, value);
        return this;
    }

    public <A, V> AddOperationBuilder add(UnaryAddDescriptor<A, V> descriptor, A firstParameter, V value) {
        Path path = descriptor.createPath(firstParameter);
        builder.setValue(path, value);
        return this;
    }

    public <A, B, V> AddOperationBuilder add(BinaryAddDescriptor<A, B, V> descriptor, A firstParameter, B secondParameter, V value) {
        Path path = descriptor.createPath(firstParameter, secondParameter);
        builder.setValue(path, value);
        return this;
    }

    public <A, B, C, V> AddOperationBuilder add(TernaryAddDescriptor<A, B, C, V> descriptor, A firstParameter, B secondParameter, C thirdParameter, V value) {
        Path path = descriptor.createPath(firstParameter, secondParameter, thirdParameter);
        builder.setValue(path, value);
        return this;
    }

    public OperationDto build() {
        Path commonPath = builder.compact();
        return OperationDto.of(commonPath.toString(), CommandType.ADD, builder.build());
    }

}
