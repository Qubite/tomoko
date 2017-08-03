package io.qubite.tomoko.direct;

import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;
import io.qubite.tomoko.direct.patch.PatchBuilder;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.descriptor.SpecificationDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;

public class DirectTomoko {

    private final Tomoko tomoko;

    DirectTomoko(Tomoko tomoko) {
        this.tomoko = tomoko;
    }

    public static DirectTomoko instance() {
        return instance(TomokoConfigurationBuilder.base());
    }

    public static DirectTomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new DirectTreeParser());
        Tomoko tomoko = Tomoko.instance(configurationBuilder.build());
        return new DirectTomoko(tomoko);
    }

    public HandlerConfigurationDSL specificationDsl() {
        return tomoko.specificationDsl();
    }

    public PatcherTreeSpecification scanHandlerTree(Object specification) {
        return tomoko.scanHandlerTree(specification);
    }

    public Patcher patcher(PatcherTreeSpecification patcherTreeSpecification) {
        return tomoko.patcher(patcherTreeSpecification);
    }

    public Patcher scanPatcher(Object specification) {
        return tomoko.scanPatcher(specification);
    }

    public <T> SpecificationDescriptor<T> descriptorFor(Class<T> specificationClass) {
        return tomoko.descriptorFor(specificationClass);
    }

    public PatchBuilder patchBuilder() {
        return PatchBuilder.instance();
    }

}
