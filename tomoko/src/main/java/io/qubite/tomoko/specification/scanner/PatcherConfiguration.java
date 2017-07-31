package io.qubite.tomoko.specification.scanner;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatcherConfiguration {

    String value() default "";

    String path() default "";

}
