package io.qubite.tomoko.path.converter;

import io.qubite.tomoko.util.Preconditions;

public class IntegerConverter implements PathParameterConverter<Integer> {

    @Override
    public Integer toObject(String value) {
        Preconditions.checkNotNull(value);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConverterException("Could not parse the provided value as " + Integer.class.getSimpleName());
        }
    }

    @Override
    public String toPathString(Integer value) {
        Preconditions.checkNotNull(value);
        return value.toString();
    }

}
