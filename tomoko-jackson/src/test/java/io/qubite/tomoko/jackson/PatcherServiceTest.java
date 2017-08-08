package io.qubite.tomoko.jackson;

import io.qubite.tomoko.patch.Patch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PatcherServiceTest {

    @Test
    public void parse_singleAddOperations() throws Exception {
        PatchParser patchParser = PatchParser.instance();
        Patch patch = patchParser.parse(fromFile("/operations/singleOperation.json"));
        assertEquals(1, patch.getOperations().size());
    }

    @Test
    public void parse_validAddOperations() throws Exception {
        PatchParser patchParser = PatchParser.instance();
        Patch patch = patchParser.parse(fromFile("/operations/multipleOperation.json"));
        assertEquals(2, patch.getOperations().size());
    }

    @Test
    public void parse_complexValue() throws Exception {
        PatchParser patchParser = PatchParser.instance();
        Patch patch = patchParser.parse(fromFile("/operations/singleOperation_complexValue.json"));
        assertEquals(1, patch.getOperations().size());
    }

    @Test
    public void parse_validRemoveOperation() throws Exception {
        PatchParser patchParser = PatchParser.instance();
        Patch patch = patchParser.parse(fromFile("/operations/singleOperation_remove.json"));
        assertEquals(1, patch.getOperations().size());
    }

    private InputStream fromFile(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

}
