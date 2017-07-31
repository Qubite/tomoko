package io.qubite.tomoko.type;

public class SimpleType<T> implements ValueType<T> {

    private final Class<T> clazz;

    SimpleType(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getBaseClass() {
        return clazz;
    }

    @Override
    public String toString() {
        return clazz.getSimpleName();
    }
}
