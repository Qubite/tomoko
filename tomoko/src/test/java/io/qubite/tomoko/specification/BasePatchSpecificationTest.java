package io.qubite.tomoko.specification;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import io.qubite.tomoko.type.Types;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.junit.Assert.assertNotNull;

/**
 * Created by edhendil on 12.08.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BasePatchSpecificationTest {

    @Mock
    private Consumer<String> noop;
    @Mock
    private Runnable none;

    @Test
    public void build_singleAddHandler() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().then(PathNodes.staticNode("none"));
        builder.add(pathTemplate, Types.simple(String.class), noop);
        TreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

    @Test(expected = PatcherException.class)
    public void build_registerAddHandlerAfterExistingHandler_exception() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().then(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.then(PathNodes.staticNode("title"));
        builder.add(pathTemplate, Types.simple(String.class), noop);
        builder.add(titlePath, Types.simple(String.class), noop);
    }

    @Test(expected = PatcherException.class)
    public void build_registerAddHandlerNotOnLeaf_exception() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().then(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.then(PathNodes.staticNode("title"));
        builder.add(titlePath, Types.simple(String.class), noop);
        builder.add(pathTemplate, Types.simple(String.class), noop);
    }

    @Test
    public void build_singleRemoveHandler() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().then(PathNodes.staticNode("none"));
        builder.remove(pathTemplate, none);
        TreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

    @Test
    public void build_multipleRemoveHandlersAlongSamePath() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().then(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.then(PathNodes.staticNode("title"));
        builder.remove(pathTemplate, none);
        builder.remove(titlePath, none);
        TreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

}
