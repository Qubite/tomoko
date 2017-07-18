package io.qubite.tomoko.specification;

import io.qubite.tomoko.format.TreeTextFormat;
import io.qubite.tomoko.specification.dsl.TreeSpecificationDSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RunWith(MockitoJUnitRunner.class)
public class TreeTextFormatTest {

    @Mock
    private Consumer<String> consumer;

    @Mock
    private BiConsumer<Integer, String> biConsumer;

    @Test
    public void name() throws Exception {
        TreeSpecificationDSL root = TreeSpecificationDSL.dsl();
        root.path().node("asdf2/title").value().string().handleAdd(consumer);
        root.path().node("asdf2/description").value().string().handleAdd(consumer);
        root.path().node("asdf").integer().value().string().handleAdd(biConsumer);
        TreeSpecification tree = root.toTree();
        TreeTextFormat underTest = new TreeTextFormat();
        String stringRepresentation = underTest.treeToString(tree);
        System.out.println(stringRepresentation);
    }
}
