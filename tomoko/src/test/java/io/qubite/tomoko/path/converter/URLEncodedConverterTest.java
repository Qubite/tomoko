package io.qubite.tomoko.path.converter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class URLEncodedConverterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toObject_notNull() throws Exception {
        URLEncodedConverter underTest = new URLEncodedConverter();
        String converted = underTest.toObject("%2Fasdf%2Fqwer");
        assertEquals("/asdf/qwer", converted);
    }

    @Test
    public void toPathString_notNull() throws Exception {
        URLEncodedConverter underTest = new URLEncodedConverter();
        String converted = underTest.toPathString("/asdf/qwer");
        assertEquals("%2Fasdf%2Fqwer", converted);
    }

    @Test
    public void toObject_null_exception() throws Exception {
        URLEncodedConverter underTest = new URLEncodedConverter();
        thrown.expect(NullPointerException.class);
        underTest.toObject(null);
    }

    @Test
    public void toPathString_null_exception() throws Exception {
        URLEncodedConverter underTest = new URLEncodedConverter();
        thrown.expect(NullPointerException.class);
        underTest.toPathString(null);
    }

}
