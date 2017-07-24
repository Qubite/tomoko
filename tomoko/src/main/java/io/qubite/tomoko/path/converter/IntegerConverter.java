package io.qubite.tomoko.path.converter;

/**
 * Created by edhendil on 19.07.17.
 */
public class IntegerConverter implements PathParameterConverter<Integer> {

    @Override
    public Integer toObject(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String toPathString(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Null not accepted.");
        }
        return value.toString();
    }

}
