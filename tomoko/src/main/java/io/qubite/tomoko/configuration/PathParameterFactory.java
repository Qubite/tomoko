package io.qubite.tomoko.configuration;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.path.parameter.StringPathParameter;
import io.qubite.tomoko.path.parameter.TypedPathParameter;
import io.qubite.tomoko.specification.scanner.PathPattern;

public class PathParameterFactory {

    PathParameterFactory() {
    }

    public static PathParameterFactory instance() {
        return new PathParameterFactory();
    }

    public <T> PathParameter<T> toPathParameter(ParameterConfiguration<T> parameter, PathPattern pathPattern) {
        String parameterName = parameter.getParameterName();
        int elementIndex;
        try {
            elementIndex = pathPattern.getElementIndexByParameterName(parameterName);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Parameter " + parameterName + " does not exist on the path.");
        }
        StringPathParameter baseParameter = StringPathParameter.of(elementIndex);
        return TypedPathParameter.of(baseParameter, parameter.getConverter());
    }

}
