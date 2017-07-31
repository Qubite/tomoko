package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.patch.CommandType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatcherHandler {

    String path();

    CommandType action();

}
