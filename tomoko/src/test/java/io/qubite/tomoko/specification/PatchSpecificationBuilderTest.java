package io.qubite.tomoko.specification;

import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.node.PathNodes;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class PatchSpecificationBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ValueHandler noop;
    @Mock
    private ValuelessHandler none;

    @Test
    public void build_singleAddHandler() throws Exception {
        PatcherTreeSpecificationBuilder builder = PatcherTreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("none"));
        builder.handleAdd(pathTemplate, noop);
        PatcherTreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

    @Test
    public void build_registerAddHandlerAfterExistingHandler_exception() throws Exception {
        PatcherTreeSpecificationBuilder builder = PatcherTreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.append(PathNodes.staticNode("title"));
        builder.handleAdd(pathTemplate, noop);
        thrown.expect(TomokoException.class);
        builder.handleAdd(titlePath, noop);
    }

    @Test
    public void build_registerAddHandlerNotOnLeaf_exception() throws Exception {
        PatcherTreeSpecificationBuilder builder = PatcherTreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.append(PathNodes.staticNode("title"));
        builder.handleAdd(titlePath, noop);
        thrown.expect(TomokoException.class);
        builder.handleAdd(pathTemplate, noop);
    }

    @Test
    public void build_singleRemoveHandler() throws Exception {
        PatcherTreeSpecificationBuilder builder = PatcherTreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("none"));
        builder.handleRemove(pathTemplate, none);
        PatcherTreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

    @Test
    public void build_multipleRemoveHandlersAlongSamePath() throws Exception {
        PatcherTreeSpecificationBuilder builder = PatcherTreeSpecification.builder();
        PathTemplate pathTemplate = PathTemplate.empty().append(PathNodes.staticNode("ticket"));
        PathTemplate titlePath = pathTemplate.append(PathNodes.staticNode("title"));
        builder.handleRemove(pathTemplate, none);
        builder.handleRemove(titlePath, none);
        PatcherTreeSpecification patchSpecification = builder.build();
        assertNotNull(patchSpecification);
    }

}
