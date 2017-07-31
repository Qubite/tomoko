package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.specification.annotation.LinkedConfiguration;

public class LinkingSiblingSpecification {

    private final TestSpecification linked;

    public LinkingSiblingSpecification(TestSpecification linked) {
        this.linked = linked;
    }

    @LinkedConfiguration
    public TestSpecification getLinked() {
        return linked;
    }

}
