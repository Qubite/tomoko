package io.qubite.tomoko.path.converter;

/**
 * Created by edhendil on 19.07.17.
 */
public class IdentityConverter implements PathParameterConverter<String> {

    @Override
    public String toObject(String value) {
        return value;
    }

    @Override
    public String toPathString(String value) {
        return value;
    }

}
