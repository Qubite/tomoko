package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;

public class JacksonTomoko {

    public static Tomoko instance() {
        return instance(new ObjectMapper());
    }

    public static Tomoko instance(ObjectMapper mapper) {
        return instance(TomokoConfigurationBuilder.base(), mapper);
    }

    public static Tomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        return instance(configurationBuilder, new ObjectMapper());
    }

    public static Tomoko instance(TomokoConfigurationBuilder configurationBuilder, ObjectMapper mapper) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new JacksonParser(mapper));
        return Tomoko.instance(configurationBuilder.build());
    }

}
