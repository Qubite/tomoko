package io.qubite.tomoko.path.converter;

public class Converters {

    private Converters() {
    }

    public static PathParameterConverter<String> identity() {
        return new IdentityConverter();
    }

    public static PathParameterConverter<Integer> integer() {
        return new IntegerConverter();
    }

    public static PathParameterConverter<Long> longConverter() {
        return new LongConverter();
    }

    public static PathParameterConverter<String> urlEncoded() {
        return new URLEncodedConverter();
    }

}
