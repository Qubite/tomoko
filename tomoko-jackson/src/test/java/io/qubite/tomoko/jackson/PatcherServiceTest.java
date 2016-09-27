package io.qubite.tomoko.jackson;

import io.qubite.tomoko.json.Patch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by edhendil on 12.08.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PatcherServiceTest {

    @Test
    public void parse_singleAddOperations() throws Exception {
        PatchFactory patchFactory = PatchFactory.instance();
        Patch patch = patchFactory.parse(fromFile("/operations/singleOperation.json"));
        assertEquals(1, patch.getOperations().size());
    }

    @Test
    public void parse_validAddOperations() throws Exception {
        PatchFactory patchFactory = PatchFactory.instance();
        Patch patch = patchFactory.parse(fromFile("/operations/multipleOperation.json"));
        assertEquals(2, patch.getOperations().size());
    }

    @Test
    public void parse_complexValue() throws Exception {
        PatchFactory patchFactory = PatchFactory.instance();
        Patch patch = patchFactory.parse(fromFile("/operations/singleOperation_complexValue.json"));
        assertEquals(1, patch.getOperations().size());
    }

    @Test
    public void parse_validRemoveOperation() throws Exception {
        PatchFactory patchFactory = PatchFactory.instance();
        Patch patch = patchFactory.parse(fromFile("/operations/singleOperation_remove.json"));
        assertEquals(1, patch.getOperations().size());
    }

    private InputStream fromFile(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

}
