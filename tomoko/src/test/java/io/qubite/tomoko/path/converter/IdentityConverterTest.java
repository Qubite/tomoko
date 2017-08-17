package io.qubite.tomoko.path.converter;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class IdentityConverterTest {

    @Test
    public void toObject_notNull() throws Exception {
        IdentityConverter underTest = new IdentityConverter();
        String converted = underTest.toObject("String");
        assertEquals("String", converted);
    }

    @Test
    public void toPathString_notNull() throws Exception {
        IdentityConverter underTest = new IdentityConverter();
        String converted = underTest.toPathString("String");
        assertEquals("String", converted);
    }

    @Test
    public void toObject_null() throws Exception {
        IdentityConverter underTest = new IdentityConverter();
        String converted = underTest.toObject(null);
        assertNull(converted);
    }

    @Test
    public void toPathString_null() throws Exception {
        IdentityConverter underTest = new IdentityConverter();
        String converted = underTest.toPathString(null);
        assertNull(converted);
    }

}
