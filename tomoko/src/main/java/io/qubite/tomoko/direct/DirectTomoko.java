package io.qubite.tomoko.direct;

import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;
import io.qubite.tomoko.direct.patch.PatchBuilder;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.descriptor.SpecificationDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;

/**
 * Central Tomoko library facade specialized for use with {@link DirectTree}. Quick and easy way to access all important elements.
 */
public class DirectTomoko {

    private final Tomoko tomoko;

    DirectTomoko(Tomoko tomoko) {
        this.tomoko = tomoko;
    }

    /**
     * Creates a new DirectTomoko instance. Automatically registers the {@link DirectTreeParser}.
     *
     * @return
     */
    public static DirectTomoko instance() {
        return instance(TomokoConfigurationBuilder.base());
    }

    /**
     * Creates a new DirectTomoko instance using the provided configuration as the basis. Automatically registers the {@link DirectTreeParser}.
     *
     * @param configurationBuilder
     * @return
     */
    public static DirectTomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new DirectTreeParser());
        Tomoko tomoko = Tomoko.instance(configurationBuilder.build());
        return new DirectTomoko(tomoko);
    }

    /**
     * Allows registering handlers programmatically in a type-safe way.
     *
     * @return
     * @see io.qubite.tomoko.specification.dsl
     */
    public HandlerConfigurationDSL specificationDsl() {
        return tomoko.specificationDsl();
    }

    /**
     * Scans the target object and registers all properly annotated handlers.
     *
     * @param specification
     * @return handler tree model
     */
    public PatcherTreeSpecification scanHandlerTree(Object specification) {
        return tomoko.scanHandlerTree(specification);
    }

    /**
     * Scans the target object and registers all properly annotated handlers.
     *
     * @param specification
     * @return patcher ready to be used
     */
    public Patcher patcher(PatcherTreeSpecification specification) {
        return tomoko.patcher(specification);
    }

    /**
     * Converts a handler tree model to a patcher.
     *
     * @param specification
     * @return
     */
    public Patcher scanPatcher(Object specification) {
        return tomoko.scanPatcher(specification);
    }

    /**
     * Used to retrieve descriptors for handlers found in the provided class. Allows for type-safe patch creation.
     *
     * @param specificationClass class with methods annotated as handlers
     * @param <T>
     * @return
     */
    public <T> SpecificationDescriptor<T> descriptorFor(Class<T> specificationClass) {
        return tomoko.descriptorFor(specificationClass);
    }

    /**
     * Creates a builder to define a patch instance.
     *
     * @return
     */
    public PatchBuilder patchBuilder() {
        return PatchBuilder.instance();
    }

}
