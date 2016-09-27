package io.qubite.tomoko.type;

/**
 * Created by edhendil on 18.08.16.
 */
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
