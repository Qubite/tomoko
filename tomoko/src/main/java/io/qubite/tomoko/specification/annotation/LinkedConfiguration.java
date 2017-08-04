package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * Links child specification to the parent one.
 * </p>
 * <p>
 * Can be used on a getter method or on a field. However only the method level annotation is supported in {@link io.qubite.tomoko.specification.descriptor.SpecificationDescriptor}.
 * </p>
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LinkedConfiguration {

    /**
     * Prefix for all handlers found in the child specification.
     */
    String path() default "";

}
