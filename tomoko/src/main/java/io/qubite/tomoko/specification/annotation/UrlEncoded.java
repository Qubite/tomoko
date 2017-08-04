package io.qubite.tomoko.specification.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlEncoded {

    String value() default "";

}
