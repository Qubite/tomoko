package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.Patcher;
import io.qubite.tomoko.PatcherFactory;
import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.specification.PatcherSpecificationBuilder;

public class ConfigurationDSL {

    private final PatcherSpecificationBuilder builder;

    ConfigurationDSL(PatcherSpecificationBuilder builder) {
        this.builder = builder;
    }

    public static ConfigurationDSL dsl() {
        return new ConfigurationDSL(PatcherSpecification.builder());
    }

    public NullaryPath path() {
        return new NullaryPath(HandlerFactory.instance(), builder, PathTemplate.empty());
    }

    public PatcherSpecification toTree() {
        return builder.build();
    }

    public Patcher toPatcher() {
        return PatcherFactory.instance().create(builder.build());
    }

}
