package io.qubite.tomoko.specification;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BasePatchSpecificationTest {

    @Mock
    private ValueHandler noop;
    @Mock
    private ValuelessHandler none;

    @Test
    public void build_singleAddHandler() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("none"));
        builder.handleAdd(pathTemplate, noop);
        TreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

    @Test(expected = PatcherException.class)
    public void build_registerAddHandlerAfterExistingHandler_exception() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.append(PathNodes.staticNode("title"));
        builder.handleAdd(pathTemplate, noop);
        builder.handleAdd(titlePath, noop);
    }

    @Test(expected = PatcherException.class)
    public void build_registerAddHandlerNotOnLeaf_exception() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.append(PathNodes.staticNode("title"));
        builder.handleAdd(titlePath, noop);
        builder.handleAdd(pathTemplate, noop);
    }

    @Test
    public void build_singleRemoveHandler() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("none"));
        builder.handleRemove(pathTemplate, none);
        TreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

    @Test
    public void build_multipleRemoveHandlersAlongSamePath() throws Exception {
        TreeSpecificationBuilder builder = TreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.append(PathNodes.staticNode("title"));
        builder.handleRemove(pathTemplate, none);
        builder.handleRemove(titlePath, none);
        TreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

}
