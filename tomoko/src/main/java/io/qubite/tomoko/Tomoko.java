package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ParserConverterFactory;
import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.patcher.PatcherBase;
import io.qubite.tomoko.resolver.TreeHandlerResolver;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.descriptor.SpecificationDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;
import io.qubite.tomoko.specification.scanner.ClassScanner;

/**
 * Central Tomoko library facade. Quick and easy way to access all important elements.
 */
public class Tomoko {

    private final TomokoConfiguration configuration;

    Tomoko(TomokoConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Creates a new Tomoko instance using the provided configuration.
     *
     * @param configuration
     * @return
     */
    public static Tomoko instance(TomokoConfiguration configuration) {
        return new Tomoko(configuration);
    }

    /**
     * Allows registering handlers programmatically in a type-safe way.
     *
     * @return
     * @see io.qubite.tomoko.specification.dsl
     */
    public HandlerConfigurationDSL specificationDsl() {
        return HandlerConfigurationDSL.dsl(ParserConverterFactory.instance(configuration));
    }

    /**
     * Scans the target object and registers all properly annotated handlers.
     *
     * @param specification
     * @return handler tree model
     */
    public PatcherTreeSpecification scanHandlerTree(Object specification) {
        return ClassScanner.instance(ParserConverterFactory.instance(configuration)).scan(specification);
    }

    /**
     * Scans the target object and registers all properly annotated handlers.
     *
     * @param specification
     * @return patcher ready to be used
     */
    public Patcher scanPatcher(Object specification) {
        return patcher(scanHandlerTree(specification));
    }

    /**
     * Converts a handler tree model to a patcher.
     *
     * @param specification
     * @return
     */
    public Patcher patcher(PatcherTreeSpecification specification) {
        return PatcherBase.instance(TreeHandlerResolver.of(specification), new OperationExecutorImpl());
    }

    /**
     * Used to retrieve descriptors for handlers found in the provided class. Allows for type-safe patch creation.
     *
     * @param specificationClass
     * @param <T>
     * @return
     */
    public <T> SpecificationDescriptor<T> descriptorFor(Class<T> specificationClass) {
        return SpecificationDescriptor.forClass(specificationClass);
    }

}
