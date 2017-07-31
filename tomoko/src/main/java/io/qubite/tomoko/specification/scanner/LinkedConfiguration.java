package io.qubite.tomoko.specification.scanner;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LinkedConfiguration {

    String path() default "";

}
