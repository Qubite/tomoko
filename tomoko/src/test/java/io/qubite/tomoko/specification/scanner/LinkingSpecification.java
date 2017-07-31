package io.qubite.tomoko.specification.scanner;

public class LinkingSpecification {

    private final TestSpecification linked;

    public LinkingSpecification(TestSpecification linked) {
        this.linked = linked;
    }

    @LinkedConfiguration(path = "/bookstore")
    public TestSpecification getLinked() {
        return linked;
    }

}
