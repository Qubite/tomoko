package io.qubite.tomoko.format;

import io.qubite.tomoko.direct.DirectTomoko;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;
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
    public void name() throws Exception {
        HandlerConfigurationDSL root = DirectTomoko.instance().dsl();
        root.path().node("asdf2/title").value().string().handleAdd(consumer);
        root.path().node("asdf2/description").value().string().handleAdd(consumer);
        root.path().node("asdf").integer().value().string().handleAdd(biConsumer);
        PatcherSpecification tree = root.toTree();
        TreeTextFormat underTest = new TreeTextFormat();
        String stringRepresentation = underTest.treeToString(tree);
        assertFalse(stringRepresentation.isEmpty());
        LOGGER.debug(stringRepresentation);
    }
}
