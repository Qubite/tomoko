package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.PatcherException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by edhendil on 19.07.17.
 */
public class URLEncodedConverter implements PathParameterConverter<String> {

    @Override
    public String toObject(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public String toPathString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null not accepted.");
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new PatcherException(e);
        }
    }

}
