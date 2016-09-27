package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.Patcher;
import io.qubite.tomoko.PatcherFactory;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.specification.dsl.TreeSpecificationDSL;
import io.qubite.tomoko.specification.dsl.UnaryAddDescriptor;
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
 * Created by edhendil on 29.08.16.
 */
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
        TreeSpecificationDSL root = TreeSpecificationDSL.root();
        UnaryAddDescriptor<Integer, String> biConsumerPath = root.path().text(TICKETS_NODE).integer().text(TITLE_NODE).add().string().handle(biConsumer);
        TreeSpecification tree = root.build();
        String providedValue = "asdf";
        int pathParameter = 1;
        Patcher patcher = PatcherFactory.instance().create(tree);
        OperationDto biConsumerOperation = OperationsDSL.create(biConsumerPath, pathParameter, providedValue).build();
        List<OperationDto> operations = new ArrayList<>();
        operations.add(biConsumerOperation);
        patcher.execute(Patch.of(operations));
        verify(biConsumer).accept(pathParameter, providedValue);
    }

}