package io.qubite.tomoko.util;

import io.qubite.tomoko.PatcherException;
import net.jodah.typetools.TypeResolver;

/**
 * Created by edhendil on 20.07.17.
 */
public class Preconditions {

    public static void checkNotNull(Object toCheck) {
        if (toCheck == null) {
            throw new NullPointerException();
        }
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
