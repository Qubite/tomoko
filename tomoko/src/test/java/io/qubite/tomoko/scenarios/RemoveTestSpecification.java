package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.specification.annotation.RemoveHandler;

public class RemoveTestSpecification {

    @RemoveHandler("/books/{bookId}/title")
    public void normalRemove() {

    }

}
