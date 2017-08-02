package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.handler.value.converter.ValueConverterFactory;
import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.patcher.PatcherBase;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.specification.PatcherSpecificationBuilder;

public class HandlerConfigurationDSL {

    private final ValueConverterFactory valueConverterFactory;
    private final PatcherSpecificationBuilder builder;

    HandlerConfigurationDSL(ValueConverterFactory valueConverterFactory, PatcherSpecificationBuilder builder) {
        this.valueConverterFactory = valueConverterFactory;
        this.builder = builder;
    }

    public static HandlerConfigurationDSL dsl(ValueConverterFactory valueConverterFactory) {
        return new HandlerConfigurationDSL(valueConverterFactory, PatcherSpecification.builder());
    }

    public NullaryPath path() {
        return new NullaryPath(HandlerFactory.instance(valueConverterFactory), builder, PathTemplate.empty());
    }

    public PatcherSpecification toTree() {
        return builder.build();
    }

    public Patcher toPatcher() {
        return PatcherBase.instance(builder.build(), new OperationExecutorImpl(new HandlerResolver()));
    }

}
