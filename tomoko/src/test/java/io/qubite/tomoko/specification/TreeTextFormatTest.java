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
        root.emptyPath().path("asdf2/title").handleAdd().string().handle(consumer);
        root.emptyPath().path("asdf2/description").handleAdd().string().handle(consumer);
        root.emptyPath().path("asdf").integer().handleAdd().string().handle(biConsumer);
        TreeSpecification tree = root.toTree();
        TreeTextFormat underTest = new TreeTextFormat();
        String stringRepresentation = underTest.treeToString(tree);
        System.out.println(stringRepresentation);
    }
}
