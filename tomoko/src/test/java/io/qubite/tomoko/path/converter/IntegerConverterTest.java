package io.qubite.tomoko.path.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerConverterTest {

    @Test
    public void toObject_notNull() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        int converted = underTest.toObject("14");
        assertEquals(14, converted);
    }

    @Test
    public void toPathString_notNull() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        String converted = underTest.toPathString(14);
        assertEquals("14", converted);
    }

    @Test(expected = NullPointerException.class)
    public void toObject_null_exception() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        Integer converted = underTest.toObject(null);
    }

    @Test(expected = NullPointerException.class)
    public void toPathString_null_exception() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        String converted = underTest.toPathString(null);
    }

    @Test(expected = NumberFormatException.class)
    public void toObject_invalidString_exception() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        int converted = underTest.toObject("asdf");
    }

}
