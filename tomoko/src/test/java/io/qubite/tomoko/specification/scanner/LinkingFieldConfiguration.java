package io.qubite.tomoko.specification.scanner;

public class LinkingFieldConfiguration {

    @LinkedConfiguration(path = "/bookstore")
    private final TestSpecification linked;

    public LinkingFieldConfiguration(TestSpecification linked) {
        this.linked = linked;
    }

}
