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
 * Central Tomoko facade. Quick and easy way to access all necessary elements.
 */
public class Tomoko {

    private final TomokoConfiguration configuration;

    Tomoko(TomokoConfiguration configuration) {
        this.configuration = configuration;
    }

    public static Tomoko instance(TomokoConfiguration configuration) {
        return new Tomoko(configuration);
    }

    public HandlerConfigurationDSL specificationDsl() {
        return HandlerConfigurationDSL.dsl(ParserConverterFactory.instance(configuration));
    }

    public PatcherTreeSpecification scanHandlerTree(Object specification) {
        return ClassScanner.instance(ParserConverterFactory.instance(configuration)).scan(specification);
    }

    public Patcher scanPatcher(Object specification) {
        return patcher(scanHandlerTree(specification));
    }

    public Patcher patcher(PatcherTreeSpecification patcherTreeSpecification) {
        return PatcherBase.instance(TreeHandlerResolver.of(patcherTreeSpecification), new OperationExecutorImpl());
    }

    public <T> SpecificationDescriptor<T> descriptorFor(Class<T> specificationClass) {
        return SpecificationDescriptor.forClass(specificationClass);
    }

}
