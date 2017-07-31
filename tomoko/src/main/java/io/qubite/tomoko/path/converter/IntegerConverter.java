package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.util.Preconditions;

public class IntegerConverter implements PathParameterConverter<Integer> {

    @Override
    public Integer toObject(String value) {
        Preconditions.checkNotNull(value);
        return Integer.parseInt(value);
    }

    @Override
    public String toPathString(Integer value) {
        Preconditions.checkNotNull(value);
        return value.toString();
    }

}
