package io.qubite.tomoko.path;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edhendil on 29.08.16.
 */
public class PathTemplateParameters {

    private final Map<Integer, Object> parameters = new HashMap<>();

    public static PathTemplateParameters empty() {
        return new PathTemplateParameters();
    }

    public <T> void addParameter(PathTemplate<T> parameterTemplate, T value) {
        addParameter(parameterTemplate.size() - 1, value);
    }

    public <T> void addParameter(int index, T value) {
        parameters.put(index, value);
    }

    public Object getParameter(int index) {
        return parameters.get(index);
    }

}
