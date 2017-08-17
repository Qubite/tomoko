package io.qubite.tomoko.type;

public class GenericType<T> implements ValueType<T> {

    private final Class<T> baseClass;
    private final Class<?>[] parameterTypes;

    public GenericType(Class<T> baseClass, Class<?>[] parameterTypes) {
        this.baseClass = baseClass;
        this.parameterTypes = parameterTypes;
    }

    public Class<T> getBaseClass() {
        return baseClass;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(baseClass.getSimpleName());
        builder.append("<");
        for (Class<?> clazz : parameterTypes) {
            builder.append(clazz.getSimpleName());
            builder.append(", ");
        }
        if (parameterTypes.length > 0) {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(">");
        return builder.toString();
    }
}
