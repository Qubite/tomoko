package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.specification.annotation.RemoveHandler;

public class DuplicatePathSpecification {

    @RemoveHandler("/book")
    public void entryOne() {

    }

    @RemoveHandler("/book")
    public void entryTwo() {

    }

}
