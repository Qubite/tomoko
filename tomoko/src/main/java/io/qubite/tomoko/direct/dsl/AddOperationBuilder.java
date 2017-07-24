package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.direct.DirectTreeBuilder;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.path.Path;

public class AddOperationBuilder {

    private final DirectTreeBuilder builder;

    AddOperationBuilder(DirectTreeBuilder builder) {
        this.builder = builder;
    }

    public static AddOperationBuilder create() {
        return new AddOperationBuilder(DirectTree.builder());
    }

    public <V> AddOperationBuilder add(Path path, V value) {
        builder.setValue(path, value);
        return this;
    }

    public OperationDto build() {
        Path commonPath = builder.compact();
        return OperationDto.of(commonPath.toString(), CommandType.ADD, builder.build());
    }

}
