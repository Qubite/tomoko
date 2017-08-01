package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.Patcher;
import io.qubite.tomoko.PatcherFactory;
import io.qubite.tomoko.TomokoConfiguration;
import io.qubite.tomoko.direct.patch.PatchBuilder;
import io.qubite.tomoko.handler.value.converter.ParserConverterFactory;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.specification.PatcherSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ClassScannerTest {

    @Test
    public void scanClass_simple() throws Exception {
        TomokoConfiguration tomokoConfiguration = TomokoConfiguration.direct();
        ClassScanner scanner = new ClassScanner(ParserConverterFactory.instance(tomokoConfiguration));
        TestSpecification toScan = new TestSpecification();
        PatcherSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchBuilder.dsl().add("/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", toScan.getLastBookId());
        assertEquals("new title", toScan.getLastTitle());
    }

    @Test
    public void scanClass_linkedAtPath() throws Exception {
        TomokoConfiguration tomokoConfiguration = TomokoConfiguration.direct();
        ClassScanner scanner = new ClassScanner(ParserConverterFactory.instance(tomokoConfiguration));
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingSpecification toScan = new LinkingSpecification(simpleSpecification);
        PatcherSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchBuilder.dsl().add("/bookstore/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

    @Test
    public void scanClass_linkedAtPathThroughField() throws Exception {
        TomokoConfiguration tomokoConfiguration = TomokoConfiguration.direct();
        ClassScanner scanner = new ClassScanner(ParserConverterFactory.instance(tomokoConfiguration));
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingFieldConfiguration toScan = new LinkingFieldConfiguration(simpleSpecification);
        PatcherSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchBuilder.dsl().add("/bookstore/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

    @Test
    public void scanClass_linkedAsSibling() throws Exception {
        TomokoConfiguration tomokoConfiguration = TomokoConfiguration.direct();
        ClassScanner scanner = new ClassScanner(ParserConverterFactory.instance(tomokoConfiguration));
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingSiblingSpecification toScan = new LinkingSiblingSpecification(simpleSpecification);
        PatcherSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchBuilder.dsl().add("/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

}
