package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

/**
 * Marks a method as an ADD operation handler. Shorthand for @PatcherHandler(action = CommandType.ADD).
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddHandler {

    /**
     * Path this handler will be registered on.
     */
    String value();

}
