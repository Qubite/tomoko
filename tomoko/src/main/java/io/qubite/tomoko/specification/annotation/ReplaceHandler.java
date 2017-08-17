package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

/**
 * Marks a method as an REPLACE operation handler. Shorthand for @PatcherHandler(action = CommandType.REPLACE).
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplaceHandler {

    /**
     * Path this handler will be registered on.
     */
    String value();

}
