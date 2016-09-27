package io.qubite.tomoko.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by edhendil on 18.08.16.
 */
class CustomParametrizedType implements ParameterizedType {

    private final Class<?> baseClass;
    private final Class<?>[] parameters;

    CustomParametrizedType(Class<?> baseClass, Class<?>[] parameters) {
        this.baseClass = baseClass;
        this.parameters = parameters;
    }

    static CustomParametrizedType of(Class<?> baseClass, Class<?>... parameters) {
        return new CustomParametrizedType(baseClass, parameters);
    }

    @Override
    public Type[] getActualTypeArguments() {
        return parameters;
    }

    @Override
    public Type getRawType() {
        return baseClass;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

}
