package io.qubite.tomoko.path.converter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class LongConverterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toObject_notNull() throws Exception {
        LongConverter underTest = new LongConverter();
        long converted = underTest.toObject("14");
        assertEquals(14, converted);
    }

    @Test
    public void toPathString_notNull() throws Exception {
        LongConverter underTest = new LongConverter();
        String converted = underTest.toPathString(14l);
        assertEquals("14", converted);
    }

    @Test
    public void toObject_null_exception() throws Exception {
        LongConverter underTest = new LongConverter();
        thrown.expect(NullPointerException.class);
        underTest.toObject(null);
    }

    @Test
    public void toPathString_null_exception() throws Exception {
        LongConverter underTest = new LongConverter();
        thrown.expect(NullPointerException.class);
        underTest.toPathString(null);
    }

    @Test
    public void toObject_invalidString_exception() throws Exception {
        LongConverter underTest = new LongConverter();
        thrown.expect(NumberFormatException.class);
        underTest.toObject("asdf");
    }

}
