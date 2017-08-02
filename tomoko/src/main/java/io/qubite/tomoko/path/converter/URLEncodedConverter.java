package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.util.Preconditions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLEncodedConverter implements PathParameterConverter<String> {

    @Override
    public String toObject(String value) {
        Preconditions.checkNotNull(value);
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toPathString(String value) {
        Preconditions.checkNotNull(value);
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
