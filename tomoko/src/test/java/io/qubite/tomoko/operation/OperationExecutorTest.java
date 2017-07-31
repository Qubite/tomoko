package io.qubite.tomoko.operation;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.direct.Operations;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.specification.PatcherSpecificationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OperationExecutorTest {

    private OperationExecutorImpl operationExecutor = new OperationExecutorImpl(new HandlerResolver());

    @Mock
    private ValueHandler valueHandler;
    @Mock
    private ValueHandler valueHandlerWithParameter;
    @Mock
    private ValuelessHandler valuelessHandler;

    @Test
    public void execute_validAddOperations() throws Exception {
        PatcherSpecification specification = createPatcher();
        operationExecutor.execute(specification, createMultipleOperation());
        verify(valueHandler).execute(Matchers.any(), Matchers.any());
        verify(valueHandlerWithParameter).execute(Matchers.any(), Matchers.any());
    }

    private Patch createMultipleOperation() {
        List<OperationDto> operations = new ArrayList<>();
        operations.add(Operations.add(Path.of("asdf"), DirectTree.builder().setValue(Path.empty(), "qwer").build()));
        operations.add(Operations.add(Path.of("asdf33", "2"), DirectTree.builder().setValue(Path.empty(), "rewq").build()));
        return Patch.of(operations);
    }

    @Test(expected = PatcherException.class)
    public void execute_missingHandlers_exception() throws Exception {
        PatcherSpecification specification = createEmptyPatcher();
        operationExecutor.execute(specification, createMultipleOperation());
    }

    @Test
    public void execute_validRemoveOperation() throws Exception {
        PatcherSpecification specification = createPatcher();
        operationExecutor.execute(specification, createSingleRemoveOperation());
        verify(valuelessHandler).execute(Matchers.any());
    }

    private Patch createSingleRemoveOperation() {
        List<OperationDto> operations = new ArrayList<>();
        operations.add(Operations.remove(Path.of("asdf")));
        return Patch.of(operations);
    }

    @Test
    public void execute_deepHandlerForComplexValue() throws Exception {
        PatcherSpecification specification = createPatcher();
        operationExecutor.execute(specification, createSingleComplexValueAsdf());
        verify(valueHandler).execute(Matchers.any(), Matchers.any());
        verify(valueHandlerWithParameter).execute(Matchers.any(), Matchers.any());
    }

    @Test(expected = PatcherException.class)
    public void execute_stringValueInIntegerNode_exception() throws Exception {
        PatcherSpecification specification = createPatcher();
        DirectTree value = DirectTree.of("stringValue");
        OperationDto addOperation = Operations.add("/asdf33/stringValue", value);
        operationExecutor.execute(specification, addOperation);
    }

    @Test(expected = PatcherException.class)
    public void execute_removeOperationWithoutHandler_exception() throws Exception {
        PatcherSpecificationBuilder builder = PatcherSpecification.builder();
        PathTemplate asdfDeepPath = PathTemplate.empty().append(PathNodes.staticNode("author")).append(PathNodes.staticNode("firstName"));
        builder.handleRemove(asdfDeepPath, valuelessHandler);
        PatcherSpecification specification = builder.build();
        OperationDto operation = Operations.remove("/author");
        operationExecutor.execute(specification, operation);
    }

    @Test
    public void execute_replaceOperation() throws Exception {
        PatcherSpecificationBuilder builder = PatcherSpecification.builder();
        PathTemplate asdfDeepPath = PathTemplate.empty().append(PathNodes.staticNode("author")).append(PathNodes.staticNode("firstName"));
        builder.handleReplace(asdfDeepPath, valueHandler);
        PatcherSpecification specification = builder.build();
        DirectTree value = DirectTree.of("stringValue");
        OperationDto operation = Operations.replace("/author/firstName", value);
        operationExecutor.execute(specification, operation);
        verify(valueHandler).execute(Matchers.any(), Matchers.any());
    }

    private Patch createSingleComplexValueAsdf() {
        List<OperationDto> operations = new ArrayList<>();
        DirectTree valueTree = DirectTree.builder().setValue(Path.of("asdf"), "qwer").setValue(Path.of("asdf33", "2"), "qerw").build();
        operations.add(Operations.add(Path.empty(), valueTree));
        return Patch.of(operations);
    }

    private PatcherSpecification createPatcher() {
        PatcherSpecificationBuilder builder = PatcherSpecification.builder();
        PathTemplate asdfPath = PathTemplate.empty().append(PathNodes.staticNode("asdf"));
        PathTemplate integerNodePath = PathTemplate.empty().append(PathNodes.staticNode("asdf33")).append(PathNodes.integerNode());
        builder.handleAdd(asdfPath, valueHandler);
        builder.handleAdd(integerNodePath, valueHandlerWithParameter);
        builder.handleRemove(asdfPath, valuelessHandler);
        return builder.build();
    }

    private PatcherSpecification createEmptyPatcher() {
        PatcherSpecificationBuilder builder = PatcherSpecification.builder();
        return builder.build();
    }

}
