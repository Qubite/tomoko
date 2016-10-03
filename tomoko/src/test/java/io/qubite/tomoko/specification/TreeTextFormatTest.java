package io.qubite.tomoko.specification;

import io.qubite.tomoko.format.TreeTextFormat;
import io.qubite.tomoko.specification.dsl.TreeSpecificationDSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by edhendil on 30.08.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TreeTextFormatTest {

    @Mock
    private Consumer<String> consumer;

    @Mock
    private BiConsumer<Integer, String> biConsumer;

    @Test
    public void name() throws Exception {
        TreeSpecificationDSL root = TreeSpecificationDSL.root();
        root.path().text("asdf2").text("title").add().string().handle(consumer);
        root.path().text("asdf2").text("description").add().string().handle(consumer);
        root.path().text("asdf").integer().add().string().handle(biConsumer);
        TreeSpecification tree = root.toTree();
        TreeTextFormat underTest = new TreeTextFormat();
        String stringRepresentation = underTest.treeToString(tree);
        System.out.println(stringRepresentation);
    }
}
