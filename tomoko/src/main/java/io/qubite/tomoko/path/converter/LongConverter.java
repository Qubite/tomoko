package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.util.Preconditions;

public class LongConverter implements PathParameterConverter<Long> {

    @Override
    public Long toObject(String value) {
        Preconditions.checkNotNull(value);
        return Long.parseLong(value);
    }

    @Override
    public String toPathString(Long value) {
        Preconditions.checkNotNull(value);
        return value.toString();
    }

}
