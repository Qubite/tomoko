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

/**
 * Domain specific language for patch method registration.
 * <br/><br/>
 * Allows specifying the path, handler and handler parameter configuration in a type safe way.
 */
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

    /**
     * Creates an ongoing path configuration. For accepted syntax check the {@link io.qubite.tomoko.specification.dsl.PathDSL} class.
     *
     * @param uriLikePath starting path
     * @return ongoing path configuration
     */
    public PathDSL path(String uriLikePath) {
        return new PathDSL(PathPattern.empty(), builder, HandlerFactory.instance(valueConverterFactory)).path(uriLikePath);
    }

    /**
     * Scans an object and registers all properly annotated methods as handlers.
     * @param specification
     * @return
     */
    public HandlerConfigurationDSL scan(Object specification) {
        ClassScanner.instance(valueConverterFactory).scanToBuilder(builder, specification);
        return this;
    }

    /**
     * Converts registered handlers to a tree model. Useful for debugging. Completes the configuration phase.
     * @return
     */
    public PatcherTreeSpecification toTree() {
        return builder.build();
    }

    /**
     * Creates a patcher object ready to be used. Completes the configuration phase.
     * @return
     */
    public Patcher toPatcher() {
        return PatcherBase.instance(TreeHandlerResolver.of(builder.build()), new OperationExecutorImpl());
    }

}
