package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ParserConverterFactory;
import io.qubite.tomoko.operation.OperationExecutorImpl;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.patcher.PatcherBase;
import io.qubite.tomoko.resolver.HandlerResolver;
import io.qubite.tomoko.specification.PatcherSpecification;
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

    public Patcher patcher(PatcherSpecification patchSpecification) {
        return PatcherBase.instance(patchSpecification, new OperationExecutorImpl(new HandlerResolver()));
    }

    public PatcherSpecification scanSpecification(Object specification) {
        return ClassScanner.instance(ParserConverterFactory.instance(configuration)).build(specification);
    }

    public Patcher scanPatcher(Object specification) {
        return patcher(scanSpecification(specification));
    }

    public <T> SpecificationDescriptor<T> descriptorFor(Class<T> specificationClass) {
        return SpecificationDescriptor.forClass(specificationClass);
    }

}
