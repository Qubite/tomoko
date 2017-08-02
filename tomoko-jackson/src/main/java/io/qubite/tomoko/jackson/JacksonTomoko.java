package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patcher.Patcher;
import io.qubite.tomoko.specification.PatcherSpecification;
import io.qubite.tomoko.specification.descriptor.SpecificationDescriptor;
import io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL;

import java.io.InputStream;

public class JacksonTomoko {

    private final Tomoko tomoko;
    private final PatchFactory patchParser;

    JacksonTomoko(Tomoko tomoko, PatchFactory patchParser) {
        this.tomoko = tomoko;
        this.patchParser = patchParser;
    }

    public static JacksonTomoko instance() {
        return instance(new ObjectMapper());
    }

    public static JacksonTomoko instance(ObjectMapper mapper) {
        return instance(TomokoConfigurationBuilder.base(), mapper);
    }

    public static JacksonTomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        return instance(configurationBuilder, new ObjectMapper());
    }

    public static JacksonTomoko instance(TomokoConfigurationBuilder configurationBuilder, ObjectMapper mapper) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new JacksonParser(mapper));
        Tomoko tomoko = Tomoko.instance(configurationBuilder.build());
        return new JacksonTomoko(tomoko, PatchFactory.instance(mapper));
    }

    public HandlerConfigurationDSL specificationDsl() {
        return tomoko.specificationDsl();
    }

    public Patcher patcher(PatcherSpecification patchSpecification) {
        return tomoko.patcher(patchSpecification);
    }

    public PatcherSpecification scanSpecification(Object specification) {
        return tomoko.scanSpecification(specification);
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

    public Patch parsePatch(JsonNode jsonNode) {
        return patchParser.parse(jsonNode);
    }

}
