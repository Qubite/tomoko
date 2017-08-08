package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.descriptor.SpecificationDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;

/**
 * Central Tomoko library facade specialized for use with Jackson. Quick and easy way to access all important elements.
 */
public class JacksonTomoko {

    private final Tomoko tomoko;
    private final ObjectMapper mapper;

    JacksonTomoko(Tomoko tomoko, ObjectMapper mapper) {
        this.tomoko = tomoko;
        this.mapper = mapper;
    }

    /**
     * Creates a new JacksonTomoko instance using a default {@link ObjectMapper}. Automatically registers the {@link JacksonParser} instance.
     *
     * @return
     */
    public static JacksonTomoko instance() {
        return instance(new ObjectMapper());
    }

    /**
     * Creates a new JacksonTomoko instance using the provided mapper. Automatically registers the {@link JacksonParser} instance.
     *
     * @param mapper
     * @return
     */
    public static JacksonTomoko instance(ObjectMapper mapper) {
        return instance(TomokoConfigurationBuilder.base(), mapper);
    }

    /**
     * Creates a new JacksonTomoko instance using the provided configuration as the basis. Automatically registers the {@link JacksonParser} instance.
     *
     * @param configurationBuilder
     * @return
     */
    public static JacksonTomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        return instance(configurationBuilder, new ObjectMapper());
    }

    /**
     * Creates a new JacksonTomoko instance using the provided configuration as the basis. Automatically registers the {@link JacksonParser} instance using the provided mapper.
     *
     * @param configurationBuilder
     * @param mapper
     * @return
     */
    public static JacksonTomoko instance(TomokoConfigurationBuilder configurationBuilder, ObjectMapper mapper) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new JacksonParser(mapper));
        Tomoko tomoko = Tomoko.instance(configurationBuilder.build());
        return new JacksonTomoko(tomoko, mapper);
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

    public PatchParser getPatchParser() {
        return PatchParser.instance(mapper);
    }

}
