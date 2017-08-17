package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.specification.annotation.Parameter;
import io.qubite.tomoko.specification.annotation.RemoveHandler;

public class InvalidParameterNameSpecification {

    @RemoveHandler("/books/{bookId}")
    public void invalidParameterName(@Parameter("wrongName") String bookId) {

    }

}
