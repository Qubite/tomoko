package io.qubite.tomoko.direct.patch;

import io.qubite.tomoko.direct.DirectTomoko;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.specification.descriptor.value.UnaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;
import io.qubite.tomoko.type.Types;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.BiConsumer;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DSLTest {

    public static final String TICKETS_NODE = "tickets";
    public static final String TITLE_NODE = "title";
    public static final String TICKET_ID = "ticketId";

    @Mock
    private BiConsumer<Integer, String> biConsumer;

    @Test
    public void validSimpleTreeAndOperation_mockedMethods() throws Exception {
        HandlerConfigurationDSL dsl = DirectTomoko.instance().specificationDsl();
        UnaryValueHandlerDescriptor<Integer, String> registeredHandler = dsl.path().node(TICKETS_NODE).integer(TICKET_ID).node(TITLE_NODE).handleAdd(biConsumer).firstArgument(TICKET_ID, Integer.class).type(Types.string()).register();
        String providedValue = "asdf";
        int pathParameter = 1;
        Patcher patcher = dsl.toPatcher();
        Patch patch = PatchBuilder.instance().add(registeredHandler.generatePath(1), providedValue).toOperation().toPatch();
        patcher.execute(patch);
        verify(biConsumer).accept(pathParameter, providedValue);
    }

    @Test
    public void validSimpleTreeAndOperation_realMethods() throws Exception {
        HandlerConfigurationDSL dsl = DirectTomoko.instance().specificationDsl();
        dsl.path().node(TICKETS_NODE).integer(TICKET_ID).node(TITLE_NODE).handleAdd(this::realConsumer).firstArgument(TICKET_ID).register();
        Patcher patcher = dsl.toPatcher();
    }

    public void realConsumer(Integer argument, String value) {

    }

}
