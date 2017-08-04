package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

/**
 * Provides additional details about parameters of a handler. Shorthand for @Parameter(converter = {@link io.qubite.tomoko.path.converter.URLEncodedConverter URLEncodedConverter}.class).
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlEncoded {

    /**
     * Name of this parameter.<br/>
     * Optional.
     */
    String value() default "";

}
