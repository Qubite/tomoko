package io.qubite.tomoko.path.converter;

/**
 * Created by edhendil on 19.07.17.
 */
public class LongConverter implements PathParameterConverter<Long> {

    @Override
    public Long toObject(String value) {
        return Long.parseLong(value);
    }

    @Override
    public String toPathString(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("Null not accepted.");
        }
        return value.toString();
    }

}
