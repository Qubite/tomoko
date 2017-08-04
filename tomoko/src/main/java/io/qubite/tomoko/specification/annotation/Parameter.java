package io.qubite.tomoko.specification.annotation;

import io.qubite.tomoko.path.converter.IdentityConverter;
import io.qubite.tomoko.path.converter.PathParameterConverter;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

    String value() default "";

    String name() default "";

    Class<? extends PathParameterConverter<?>> converter() default IdentityConverter.class;

}
