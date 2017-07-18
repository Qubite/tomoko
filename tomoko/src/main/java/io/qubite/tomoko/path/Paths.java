package io.qubite.tomoko.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Paths {

    private Paths() {
    }

    public static Path empty() {
        List<String> nodes = new ArrayList<>();
        return new Path(nodes);
    }

    public static Path of(String... nodes) {
        return new Path(Arrays.asList(nodes));
    }

}
