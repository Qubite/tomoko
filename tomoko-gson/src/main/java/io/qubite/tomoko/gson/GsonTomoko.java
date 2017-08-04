package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.descriptor.SpecificationDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;

import java.io.InputStream;

/**
 * Central Tomoko library facade specialized for use with Gson. Quick and easy way to access all important elements.
 */
public class GsonTomoko {

    private final Tomoko tomoko;
    private final PatchFactory patchParser;

    GsonTomoko(Tomoko tomoko, PatchFactory patchParser) {
        this.tomoko = tomoko;
        this.patchParser = patchParser;
    }

    /**
     * Creates a new GsonTomoko instance. Automatically registers the {@link GsonParser} instance.
     *
     * @return
     */
    public static GsonTomoko instance() {
        return instance(new Gson());
    }

    /**
     * Creates a new GsonTomoko instance. Automatically registers the {@link GsonParser} instance using the provided mapper.
     *
     * @param mapper
     * @return
     */
    public static GsonTomoko instance(Gson mapper) {
        return instance(TomokoConfigurationBuilder.base(), mapper);
    }

    /**
     * Creates a new GsonTomoko instance using the provided configuration as the basis. Automatically registers the {@link GsonParser} instance.
     *
     * @param configurationBuilder
     * @return
     */
    public static GsonTomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        return instance(configurationBuilder, new Gson());
    }

    /**
     * Creates a new GsonTomoko instance using the provided configuration as the basis. Automatically registers the {@link GsonParser} instance using the provided mapper.
     *
     * @param configurationBuilder
     * @param mapper
     * @return
     */
    public static GsonTomoko instance(TomokoConfigurationBuilder configurationBuilder, Gson mapper) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new GsonParser(mapper));
        Tomoko tomoko = Tomoko.instance(configurationBuilder.build());
        return new GsonTomoko(tomoko, PatchFactory.instance(mapper));
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
     * Converts a handler tree model to a patcher.
     *
     * @param specification
     * @return
     */
    public Patcher patcher(PatcherTreeSpecification specification) {
        return tomoko.patcher(specification);
    }

    /**
     * Scans the target object and registers all properly annotated handlers.
     *
     * @param specification
     * @return patcher ready to be used
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
     * Parses the provided string and returns a patch object using Gson.
     *
     * @param json
     * @return
     */
    public Patch parsePatch(String json) {
        return patchParser.parse(json);
    }

    /**
     * Parses the provided input stream and returns a patch object using Gson.
     *
     * @param inputStream
     * @return
     */
    public Patch parsePatch(InputStream inputStream) {
        return patchParser.parse(inputStream);
    }

    /**
     * Parses the provided Gson JsonElement and returns a patch object.
     *
     * @param jsonElement
     * @return
     */
    public Patch parsePatch(JsonElement jsonElement) {
        return patchParser.parse(jsonElement);
    }

}
