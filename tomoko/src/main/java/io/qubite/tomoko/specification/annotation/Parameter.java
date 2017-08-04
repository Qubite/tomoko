package io.qubite.tomoko.specification.annotation;

import io.qubite.tomoko.path.converter.IdentityConverter;
import io.qubite.tomoko.path.converter.PathParameterConverter;

import java.lang.annotation.*;

/**
 * Provides additional details about parameters of a handler. Value() is synonymous to name(), but name() takes precedence.
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

    /**
     * Name of the parameter.<br/>
     * If name() is also specified then it takies precedence.
     * Optional.
     */
    String value() default "";

    /**
     * Name of the parameter.<br/>
     * Takes precedence over value() if specified.<br/>
     * Optional.
     */
    String name() default "";

    /**
     * Converter type used to map string values to handler parameter.<br/>
     * Optional.
     */
    Class<? extends PathParameterConverter<?>> converter() default IdentityConverter.class;

}
