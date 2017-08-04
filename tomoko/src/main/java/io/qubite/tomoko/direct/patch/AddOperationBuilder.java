package io.qubite.tomoko.direct.patch;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.direct.DirectTreeBuilder;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.path.Path;

/**
 * Single ADD operation builder.
 */
public class AddOperationBuilder {

    private final DirectTreeBuilder builder;

    AddOperationBuilder(DirectTreeBuilder builder) {
        this.builder = builder;
    }

    public static AddOperationBuilder create() {
        return new AddOperationBuilder(DirectTree.builder());
    }

    /**
     * Places the provided value on the provided path.
     *
     * @param path
     * @param value
     * @param <V>
     * @return
     */
    public <V> AddOperationBuilder add(Path path, V value) {
        builder.setValue(path, value);
        return this;
    }

    /**
     * Creates a new ADD operation. Common path of all added values is extracted from the value tree as the resulting operation's path.
     *
     * @return
     */
    public OperationDto build() {
        Path commonPath = builder.compact();
        return OperationDto.of(commonPath.toString(), CommandType.ADD, builder.build());
    }

}
