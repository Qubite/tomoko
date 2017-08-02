package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.direct.DirectTomoko;
import io.qubite.tomoko.direct.patch.PatchBuilder;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patcher.Patcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ClassScannerTest {

    @Test
    public void scanClass_simple() throws Exception {
        Tomoko tomoko = DirectTomoko.instance();
        TestSpecification toScan = new TestSpecification();
        Patcher patcher = tomoko.scanPatcher(toScan);
        Patch patch = PatchBuilder.instance().add("/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", toScan.getLastBookId());
        assertEquals("new title", toScan.getLastTitle());
    }

    @Test
    public void scanClass_linkedAtPath() throws Exception {
        Tomoko tomoko = DirectTomoko.instance();
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingSpecification toScan = new LinkingSpecification(simpleSpecification);
        Patcher patcher = tomoko.scanPatcher(toScan);
        Patch patch = PatchBuilder.instance().add("/bookstore/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

    @Test
    public void scanClass_linkedAtPathThroughField() throws Exception {
        Tomoko tomoko = DirectTomoko.instance();
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingFieldConfiguration toScan = new LinkingFieldConfiguration(simpleSpecification);
        Patcher patcher = tomoko.scanPatcher(toScan);
        Patch patch = PatchBuilder.instance().add("/bookstore/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

    @Test
    public void scanClass_linkedAsSibling() throws Exception {
        Tomoko tomoko = DirectTomoko.instance();
        TestSpecification simpleSpecification = new TestSpecification();
        LinkingSiblingSpecification toScan = new LinkingSiblingSpecification(simpleSpecification);
        Patcher patcher = tomoko.scanPatcher(toScan);
        Patch patch = PatchBuilder.instance().add("/books/asdf/title", "new title").toOperation().toPatch();
        patcher.execute(patch);
        assertEquals("asdf", simpleSpecification.getLastBookId());
        assertEquals("new title", simpleSpecification.getLastTitle());
    }

}
