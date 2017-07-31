package io.qubite.tomoko.path.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class URLEncodedConverterTest {

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

    @Test(expected = NullPointerException.class)
    public void toObject_null_exception() throws Exception {
        URLEncodedConverter underTest = new URLEncodedConverter();
        underTest.toObject(null);
    }

    @Test(expected = NullPointerException.class)
    public void toPathString_null_exception() throws Exception {
        URLEncodedConverter underTest = new URLEncodedConverter();
        underTest.toPathString(null);
    }

}
