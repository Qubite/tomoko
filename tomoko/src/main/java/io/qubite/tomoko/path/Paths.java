package io.qubite.tomoko.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by edhendil on 28.08.16.
 */
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
