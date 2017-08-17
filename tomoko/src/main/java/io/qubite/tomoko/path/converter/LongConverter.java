package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.util.Preconditions;

public class LongConverter implements PathParameterConverter<Long> {

    @Override
    public Long toObject(String value) {
        Preconditions.checkNotNull(value);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ConverterException("Could not parse the provided value as " + Long.class.getSimpleName());
        }
    }

    @Override
    public String toPathString(Long value) {
        Preconditions.checkNotNull(value);
        return value.toString();
    }

}
