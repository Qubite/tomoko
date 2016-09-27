package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.PatcherException;
import net.jodah.typetools.TypeResolver;

/**
 * Created by edhendil on 03.09.16.
 */
class DslPreconditions {

    private DslPreconditions() {
    }

    public static <T> void checkNonGeneric(Class<T> clazz) {
        if (clazz.getTypeParameters().length > 0) {
            throw new PatcherException("Class " + clazz.getSimpleName() + " is generic. Use add().generic() instead.");
        }
    }

    public static <T> void checkNotUnknown(Class<T> clazz) {
        if (clazz.equals(TypeResolver.Unknown.class)) {
            throw new PatcherException("Could not extract type information. Specify it directly through add().type() methods.");
        }
    }

}
