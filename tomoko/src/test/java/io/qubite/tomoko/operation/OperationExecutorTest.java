package io.qubite.tomoko.operation;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.direct.Operations;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.Paths;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;
import io.qubite.tomoko.type.Types;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

/**
 * Created by edhendil on 12.08.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class OperationExecutorTest {

    private OperationExecutorImpl operationExecutor = new OperationExecutorImpl(new HandlerResolver());

    @Mock
    private Consumer<String> addHandler;
    @Mock
    private BiConsumer<Integer, String> addHandlerWithParameter;
    @Mock
    private Runnable removeHandler;

    @Test
    public void execute_validAddOperations() throws Exception {
        TreeSpecification specification = createPatcher();
        operationExecutor.execute(specification, createMultipleOperation());
        verify(addHandler).accept("qwer");
        verify(addHandlerWithParameter).accept(2, "rewq");
    }

    private Patch createMultipleOperation() {
        List<OperationDto> operations = new ArrayList<>();
        operations.add(Operations.add(Paths.of("asdf"), DirectTree.builder().setValue(Paths.empty(), "qwer").build()));
        operations.add(Operations.add(Paths.of("asdf33", "2"), DirectTree.builder().setValue(Paths.empty(), "rewq").build()));
        return Patch.of(operations);
    }

    @Test(expected = PatcherException.class)
    public void execute_missingHandlers_exception() throws Exception {
        TreeSpecification specification = createEmptyPatcher();
        operationExecutor.execute(specification, createMultipleOperation());
    }

    @Test(expected = PatcherException.class)
    public void execute_mismatchedValueType_exception() throws Exception {
        TreeSpecification specification = createPatcher();
        operationExecutor.execute(specification, createSingleOperationComplexValue());
    }

    private Patch createSingleOperationComplexValue() {
        List<OperationDto> operations = new ArrayList<>();
        DirectTree valueTree = DirectTree.builder().setValue(Paths.of("complexA"), "rrr").setValue(Paths.of("complexB"), "qqq").build();
        operations.add(Operations.add(Paths.of("asdf"), valueTree));
        return Patch.of(operations);
    }

    @Test
    public void execute_validRemoveOperation() throws Exception {
        TreeSpecification specification = createPatcher();
        operationExecutor.execute(specification, createSingleRemoveOperation());
        verify(removeHandler).run();
    }

    private Patch createSingleRemoveOperation() {
        List<OperationDto> operations = new ArrayList<>();
        operations.add(Operations.remove(Paths.of("asdf")));
        return Patch.of(operations);
    }

    @Test
    public void execute_deepHandlerForComplexValue() throws Exception {
        TreeSpecification specification = createPatcher();
        operationExecutor.execute(specification, createSingleComplexValueAsdf());
        verify(addHandler).accept("qwer");
        verify(addHandlerWithParameter).accept(2, "qerw");
    }

    private Patch createSingleComplexValueAsdf() {
        List<OperationDto> operations = new ArrayList<>();
        DirectTree valueTree = DirectTree.builder().setValue(Paths.of("asdf"), "qwer").setValue(Paths.of("asdf33", "2"), "qerw").build();
        operations.add(Operations.add(Paths.empty(), valueTree));
        return Patch.of(operations);
    }

    private TreeSpecification createPatcher() {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate<Void> asdfPath = PathTemplate.empty().then(PathNodes.staticNode("asdf"));
        PathTemplate<Integer> integerNodePath = PathTemplate.empty().then(PathNodes.staticNode("asdf33")).then(PathNodes.integerNode());
        builder.handleAdd(asdfPath, Types.simple(String.class), addHandler);
        builder.handleAdd(integerNodePath, Types.simple(String.class), integerNodePath, addHandlerWithParameter);
        builder.handleRemove(asdfPath, removeHandler);
        return builder.build();
    }

    private TreeSpecification createEmptyPatcher() {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        return builder.build();
    }

}
