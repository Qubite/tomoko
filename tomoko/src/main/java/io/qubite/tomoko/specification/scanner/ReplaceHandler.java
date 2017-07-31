package io.qubite.tomoko.specification.scanner;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplaceHandler {

    String value();

}
