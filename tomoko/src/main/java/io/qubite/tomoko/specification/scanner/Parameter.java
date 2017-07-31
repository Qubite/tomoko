package io.qubite.tomoko.specification.scanner;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

    String value();

}
