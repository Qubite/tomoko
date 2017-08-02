package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;

public class GsonTomoko {

    public static Tomoko instance() {
        return instance(new Gson());
    }

    public static Tomoko instance(Gson mapper) {
        return instance(TomokoConfigurationBuilder.base(), mapper);
    }

    public static Tomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        return instance(configurationBuilder, new Gson());
    }

    public static Tomoko instance(TomokoConfigurationBuilder configurationBuilder, Gson mapper) {
        configurationBuilder.clearValueParsers();
        configurationBuilder.registerValueParser(new GsonParser(mapper));
        return Tomoko.instance(configurationBuilder.build());
    }

}
