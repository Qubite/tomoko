package io.qubite.tomoko.format;

import io.qubite.tomoko.direct.DirectTomoko;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;
import io.qubite.tomoko.type.Types;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static junit.framework.TestCase.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class TreeTextFormatTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeTextFormatTest.class);

    @Mock
    private Consumer<String> consumer;

    @Mock
    private BiConsumer<Integer, String> biConsumer;

    @Test
    public void format() throws Exception {
        HandlerConfigurationDSL root = DirectTomoko.instance().specificationDsl();
        root.path("/asdf2/title").handleAdd(consumer).value(Types.string()).register();
        root.path("/asdf2/description").handleAdd(consumer).value(Types.string()).register();
        root.path("/asdf").integer("asdfId").handleAdd(biConsumer).firstArgument("asdfId", Integer.class).type(Types.string()).register();
        PatcherTreeSpecification tree = root.toTree();
        TreeTextFormat underTest = new TreeTextFormat();
        String stringRepresentation = underTest.treeToString(tree);
        assertFalse(stringRepresentation.isEmpty());
        LOGGER.debug(stringRepresentation);
    }
}
