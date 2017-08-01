package io.qubite.tomoko.direct.patch;

import io.qubite.tomoko.Patcher;
import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DSLTest {

    public static final String TICKETS_NODE = "tickets";
    public static final String TITLE_NODE = "title";

    @Mock
    private Consumer<String> consumer;
    @Mock
    private BiConsumer<Integer, String> biConsumer;

    @Test
    public void validSimpleTreeAndOperation() throws Exception {
        HandlerConfigurationDSL dsl = Tomoko.direct().dsl();
        dsl.path().node(TICKETS_NODE).integer().node(TITLE_NODE).value().string().handleAdd(biConsumer);
        String providedValue = "asdf";
        int pathParameter = 1;
        Patcher patcher = dsl.toPatcher();
        Patch patch = PatchBuilder.dsl().add(Path.of(TICKETS_NODE, "1", TITLE_NODE), providedValue).toOperation().toPatch();
        patcher.execute(patch);
        verify(biConsumer).accept(pathParameter, providedValue);
    }

}
