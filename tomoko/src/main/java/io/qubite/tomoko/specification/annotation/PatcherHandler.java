package io.qubite.tomoko.specification.annotation;

import io.qubite.tomoko.patch.CommandType;

import java.lang.annotation.*;

/**
 * Marks a method as an operation handler.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatcherHandler {

    /**
     * Path this handler will be registered on.
     */
    String path();

    /**
     * Type of operation handled by this method
     */
    CommandType action();

}
