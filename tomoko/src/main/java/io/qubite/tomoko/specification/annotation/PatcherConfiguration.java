package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

/**
 * A
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatcherConfiguration {

    /**
     * Prefix for all handlers found in this specification.
     */
    String value() default "";

}
