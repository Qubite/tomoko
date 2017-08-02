package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.direct.DirectTomoko;
import io.qubite.tomoko.direct.DirectTree;
import io.qubite.tomoko.direct.patch.PatchBuilder;
import io.qubite.tomoko.handler.HandlerExecutionException;
import io.qubite.tomoko.operation.InvalidOperationException;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.resolver.HandlerNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class TomokoPatchExceptionalScenarioTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_validPatch() throws Exception {
        Patcher patcher = createAddPatcher();
        Patch patch = PatchBuilder.instance().add("/books/12345/title", "ASDF")
                .add("/books/12345/author/firstName", "Mike")
                .add("/books/12345/author/lastName", "Tyson")
                .toOperation().toPatch();
        patcher.execute(patch);
    }

    @Test
    public void execute_addHandlerPathTooDeep() throws Exception {
        Patcher patcher = createAddPatcher();
        Patch patch = PatchBuilder.instance().add("/books/12345/author/firstName/firstLetter", "A")
                .toOperation().toPatch();
        thrown.expect(InvalidOperationException.class);
        patcher.execute(patch);
    }

    @Test
    public void execute_addHandlerValueTooDeep() throws Exception {
        Patcher patcher = createAddPatcher();
        Patch patch = PatchBuilder.instance().add("/books/12345", DirectTree.builder().setValue("/author/firstName/firstLetter", "A").build())
                .toPatch();
        thrown.expect(InvalidOperationException.class);
        patcher.execute(patch);
    }

    @Test
    public void execute_handlerThrowsRuntimeException_exceptionRethrown() throws Exception {
        Patcher patcher = createAddPatcher();
        Patch patch = PatchBuilder.instance().add("/books/12345/runtimeException", "A").toOperation()
                .toPatch();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Runtime exception");
        patcher.execute(patch);
    }

    @Test
    public void execute_handlerThrowsCheckedException_exceptionWrapped() throws Exception {
        Patcher patcher = createAddPatcher();
        Patch patch = PatchBuilder.instance().add("/books/12345/checkedException", "A").toOperation()
                .toPatch();
        thrown.expect(HandlerExecutionException.class);
        thrown.expectCause(Matchers.any(IOException.class));
        patcher.execute(patch);
    }

    @Test
    public void execute_removeHandlerPathTooDeep() throws Exception {
        Patcher patcher = createRemovePatcher();
        Patch patch = PatchBuilder.instance().remove("/books/12345/title/firstLetter")
                .toPatch();
        thrown.expect(InvalidOperationException.class);
        thrown.expectCause(Matchers.any(HandlerNotFoundException.class));
        patcher.execute(patch);
    }

    @Test
    public void execute_removeHandlerPathTooShort() throws Exception {
        Patcher patcher = createRemovePatcher();
        Patch patch = PatchBuilder.instance().remove("/books")
                .toPatch();
        thrown.expect(InvalidOperationException.class);
        thrown.expectCause(Matchers.any(HandlerNotFoundException.class));
        patcher.execute(patch);
    }

    private Patcher createAddPatcher() {
        Tomoko tomoko = DirectTomoko.instance();
        return tomoko.scanPatcher(new AddTestSpecification());
    }

    private Patcher createRemovePatcher() {
        Tomoko tomoko = DirectTomoko.instance();
        return tomoko.scanPatcher(new RemoveTestSpecification());
    }

}
