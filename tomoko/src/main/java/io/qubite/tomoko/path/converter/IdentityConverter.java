package io.qubite.tomoko.path.converter;

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
