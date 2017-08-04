package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.handler.value.converter.ValueConverterFactory;
import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.patcher.PatcherBase;
import io.qubite.tomoko.resolver.TreeHandlerResolver;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.scanner.ClassScanner;
import io.qubite.tomoko.specification.scanner.PathPattern;

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

    public PathDSL path(String uriLikePath) {
        return new PathDSL(PathPattern.empty(), builder, HandlerFactory.instance(valueConverterFactory)).path(uriLikePath);
    }

    public HandlerConfigurationDSL scan(Object specification) {
        ClassScanner.instance(valueConverterFactory).scanToBuilder(builder, specification);
        return this;
    }

    public PatcherTreeSpecification toTree() {
        return builder.build();
    }

    public Patcher toPatcher() {
        return PatcherBase.instance(TreeHandlerResolver.of(builder.build()), new OperationExecutorImpl());
    }

}
