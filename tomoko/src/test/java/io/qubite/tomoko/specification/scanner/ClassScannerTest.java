package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.Patcher;
import io.qubite.tomoko.PatcherFactory;
import io.qubite.tomoko.direct.dsl.PatchDSL;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.specification.TreeSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ClassScannerTest {

    @Test
    public void scanClass_simple() throws Exception {
        ClassScanner scanner = new ClassScanner();
        TestSpecification toScan = new TestSpecification();
        TreeSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchDSL.dsl().add("/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", toScan.getLastBookId());
        assertEquals("new title", toScan.getLastTitle());
    }

    @Test
    public void scanClass_linkedAtPath() throws Exception {
        ClassScanner scanner = new ClassScanner();
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingSpecification toScan = new LinkingSpecification(simpleSpecification);
        TreeSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchDSL.dsl().add("/bookstore/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

    @Test
    public void scanClass_linkedAtPathThroughField() throws Exception {
        ClassScanner scanner = new ClassScanner();
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingFieldConfiguration toScan = new LinkingFieldConfiguration(simpleSpecification);
        TreeSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchDSL.dsl().add("/bookstore/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

    @Test
    public void scanClass_linkedAsSibling() throws Exception {
        ClassScanner scanner = new ClassScanner();
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingSiblingSpecification toScan = new LinkingSiblingSpecification(simpleSpecification);
        TreeSpecification handlerTree = scanner.build(toScan);
        Patcher patcher = PatcherFactory.instance().create(handlerTree);
        Patch patch = PatchDSL.dsl().add("/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

}
