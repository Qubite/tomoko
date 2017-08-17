package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.specification.annotation.LinkedConfiguration;

public class LinkingFieldConfiguration {

    @LinkedConfiguration(path = "/bookstore")
    private final TestSpecification linked;

    public LinkingFieldConfiguration(TestSpecification linked) {
        this.linked = linked;
    }

}
