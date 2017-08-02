package io.qubite.tomoko.path.converter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class IntegerConverterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void toObject_null_exception() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        thrown.expect(NullPointerException.class);
        Integer converted = underTest.toObject(null);
    }

    @Test
    public void toPathString_null_exception() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        thrown.expect(NullPointerException.class);
        String converted = underTest.toPathString(null);
    }

    @Test
    public void toObject_invalidString_exception() throws Exception {
        IntegerConverter underTest = new IntegerConverter();
        thrown.expect(ConverterException.class);
        int converted = underTest.toObject("asdf");
    }

}
