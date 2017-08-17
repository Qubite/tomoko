package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.specification.annotation.RemoveHandler;

public class StaticAndWildcardNodesSpecification {

    private String invoked;

    @RemoveHandler("/books/ASDF")
    public void aaaa() {
        invoked = "staticPathNode";
    }

    @RemoveHandler("/books/{bookId}")
    public void bbbb() {
        invoked = "wildcard";
    }

    @RemoveHandler("/books/QWER")
    public void cccc() {
        invoked = "staticPathNodeTwo";
    }

    public String getInvoked() {
        return invoked;
    }

}
