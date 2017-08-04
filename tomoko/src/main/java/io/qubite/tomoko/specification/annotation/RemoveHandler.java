package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

/**
 * Marks a method as an REMOVE operation handler. Shorthand for @PatcherHandler(action = CommandType.REMOVE).
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoveHandler {

    /**
     * Path this handler will be registered on.
     */
    String value();

}
