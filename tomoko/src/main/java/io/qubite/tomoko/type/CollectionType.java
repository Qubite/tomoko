package io.qubite.tomoko.type;

import java.util.Collection;

/**
 * Created by edhendil on 18.08.16.
 */
public class CollectionType<C extends Collection<E>, E> implements ValueType<C> {

    private final Class<C> baseClass;
    private final Class<E> elementClass;

    public CollectionType(Class<C> baseClass, Class<E> elementClass) {
        this.baseClass = baseClass;
        this.elementClass = elementClass;
    }

    public Class<C> getBaseClass() {
        return baseClass;
    }

    public Class<E> getElementClass() {
        return elementClass;
    }

    @Override
    public String toString() {
        return "Collection<" + elementClass.getSimpleName() + ">";
    }
}
