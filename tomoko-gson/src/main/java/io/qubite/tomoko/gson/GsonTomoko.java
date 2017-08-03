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

public class GsonTomoko {

    private final Tomoko tomoko;
    private final PatchFactory patchParser;

    GsonTomoko(Tomoko tomoko, PatchFactory patchParser) {
        this.tomoko = tomoko;
        this.patchParser = patchParser;
    }

    public static GsonTomoko instance() {
        return instance(new Gson());
    }

    public static GsonTomoko instance(Gson mapper) {
        return instance(TomokoConfigurationBuilder.base(), mapper);
    }

    public static GsonTomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        return instance(configurationBuilder, new Gson());
    }

    public static GsonTomoko instance(TomokoConfigurationBuilder configurationBuilder, Gson mapper) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new GsonParser(mapper));
        Tomoko tomoko = Tomoko.instance(configurationBuilder.build());
        return new GsonTomoko(tomoko, PatchFactory.instance(mapper));
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

    public Patch parsePatch(String json) {
        return patchParser.parse(json);
    }

    public Patch parsePatch(InputStream inputStream) {
        return patchParser.parse(inputStream);
    }

    public Patch parsePatch(JsonElement jsonElement) {
        return patchParser.parse(jsonElement);
    }

}
