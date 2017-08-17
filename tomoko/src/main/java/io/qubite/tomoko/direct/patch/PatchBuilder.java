package io.qubite.tomoko.direct.patch;

import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Builder for {@link Patch} using the {@link DirectTree} implementation of {@link io.qubite.tomoko.patch.ValueTree}.
 * </p>
 * <p>
 * It is not as flexible as building a patch through JSON but it works great for testing purposes.
 * </p>
 */
public class PatchBuilder {

    private final List<OperationDto> operations;

    PatchBuilder(List<OperationDto> operations) {
        this.operations = operations;
    }

    public static PatchBuilder instance() {
        return new PatchBuilder(new ArrayList<>());
    }

    /**
     * Add a REMOVE operation with the provided path.
     *
     * @param path
     * @return
     */
    public PatchBuilder remove(Path path) {
        operations.add(OperationDto.remove(path.toString()));
        return this;
    }

    /**
     * Add a REMOVE operation with the provided path.
     *
     * @param path
     * @return
     */
    public PatchBuilder remove(String path) {
        operations.add(OperationDto.remove(path));
        return this;
    }

    /**
     * Add a REPLACE operation with the provided path and value. Value will be wrapped in DirectTree.
     *
     * @param path
     * @param value
     * @param <V>
     * @return
     */
    public <V> PatchBuilder replace(Path path, V value) {
        operations.add(OperationDto.replace(path.toString(), DirectTree.of(value)));
        return this;
    }

    /**
     * Add a REPLACE operation with the provided path and value. Value will be wrapped in DirectTree.
     *
     * @param path
     * @param value
     * @param <V>
     * @return
     */
    public <V> PatchBuilder replace(String path, V value) {
        operations.add(OperationDto.replace(path, DirectTree.of(value)));
        return this;
    }

    /**
     * Add an ADD operation with the provided path and value tree.
     *
     * @param path
     * @param directTree
     * @return
     */
    public PatchBuilder add(String path, DirectTree directTree) {
        operations.add(OperationDto.add(path, directTree));
        return this;
    }

    /**
     * Starts the configuration of an ADD operation with the provided path and value. Allows to create a value tree composed of multiple paths and values.
     *
     * @param path
     * @param value
     * @param <V>
     * @return
     */
    public <V> PatchAddOperationBuilder add(Path path, V value) {
        return PatchAddOperationBuilder.create(operations).add(path, value);
    }

    /**
     * Starts the configuration of an ADD operation with the provided path and value. Allows to create a value tree composed of multiple paths and values.
     *
     * @param path
     * @param value
     * @param <V>
     * @return
     */
    public <V> PatchAddOperationBuilder add(String path, V value) {
        return PatchAddOperationBuilder.create(operations).add(path, value);
    }

    /**
     * Ends configuration and returns a new patch composed of all defined operations.
     *
     * @return
     */
    public Patch toPatch() {
        return Patch.of(operations);
    }

}
