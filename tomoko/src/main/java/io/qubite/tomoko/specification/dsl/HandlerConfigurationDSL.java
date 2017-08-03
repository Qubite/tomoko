package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.handler.value.converter.ValueConverterFactory;
import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.patcher.PatcherBase;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.resolver.TreeHandlerResolver;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;

public class HandlerConfigurationDSL {

    private final ValueConverterFactory valueConverterFactory;
    private final PatcherTreeSpecificationBuilder builder;

    HandlerConfigurationDSL(ValueConverterFactory valueConverterFactory, PatcherTreeSpecificationBuilder builder) {
        this.valueConverterFactory = valueConverterFactory;
        this.builder = builder;
    }

    public static HandlerConfigurationDSL dsl(ValueConverterFactory valueConverterFactory) {
        return new HandlerConfigurationDSL(valueConverterFactory, PatcherTreeSpecification.builder());
    }

    public NullaryPath path() {
        return new NullaryPath(HandlerFactory.instance(valueConverterFactory), builder, PathTemplate.empty());
    }

    public PatcherTreeSpecification toTree() {
        return builder.build();
    }

    public Patcher toPatcher() {
        return PatcherBase.instance(TreeHandlerResolver.of(builder.build()), new OperationExecutorImpl());
    }

}
