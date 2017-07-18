package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.PatcherException;
import net.jodah.typetools.TypeResolver;

class DslPreconditions {

    private DslPreconditions() {
    }

    public static <T> void checkNonGeneric(Class<T> clazz) {
        if (clazz.getTypeParameters().length > 0) {
            throw new PatcherException("Class " + clazz.getSimpleName() + " is generic. Use value().generic() instead.");
        }
    }

    public static <T> void checkNotUnknown(Class<T> clazz) {
        if (clazz.equals(TypeResolver.Unknown.class)) {
            throw new PatcherException("Could not extract type information. Specify it directly through value() methods.");
        }
    }

}
