package io.qubite.tomoko.direct;

import io.qubite.tomoko.Tomoko;
import io.qubite.tomoko.TomokoConfigurationBuilder;

public class DirectTomoko {

    public static Tomoko instance() {
        return instance(TomokoConfigurationBuilder.base());
    }

    public static Tomoko instance(TomokoConfigurationBuilder configurationBuilder) {
        configurationBuilder.registerValueParser(new DirectTreeParser());
        return Tomoko.instance(configurationBuilder.build());
    }

}
