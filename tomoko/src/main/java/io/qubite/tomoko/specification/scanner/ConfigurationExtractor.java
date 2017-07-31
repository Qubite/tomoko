package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.path.converter.Converters;
import io.qubite.tomoko.path.converter.PathParameterConverter;
import io.qubite.tomoko.specification.annotation.*;

import java.lang.reflect.Method;

public class ConfigurationExtractor {

    ConfigurationExtractor() {
    }

    public static ConfigurationExtractor instance() {
        return new ConfigurationExtractor();
    }

    public PathParameterConverter<?> extractConverter(java.lang.reflect.Parameter parameter) {
        Class<?> parameterClass = parameter.getType();
        if (parameterClass.equals(int.class) || parameterClass.equals(Integer.class)) {
            return Converters.integer();
        } else if (parameterClass.equals(long.class) || parameterClass.equals(Long.class)) {
            return Converters.longConverter();
        } else if (parameterClass.equals(String.class)) {
            return Converters.identity();
        } else {
            throw new PatcherException("Unsupported path parameter type");
        }
    }

    public String extractParameterName(java.lang.reflect.Parameter parameter) {
        if (parameter.isAnnotationPresent(Parameter.class)) {
            Parameter namedParameter = parameter.getAnnotation(Parameter.class);
            return namedParameter.value();
        } else if (parameter.isNamePresent()) {
            return parameter.getName();
        } else {
            throw new PatcherException("Could not determine the name of a parameter. Either use @Parameter annotation or compile the code with -parameters flag (Java 8+).");
        }
    }

    public ParameterDescriptor<?> extractParameter(java.lang.reflect.Parameter parameter) {
        PathParameterConverter<?> converter = extractConverter(parameter);
        String parameterName = extractParameterName(parameter);
        return ParameterDescriptor.of(parameterName, converter);
    }

    public HandlerConfiguration extractHandlerConfiguration(Method method) {
        String path;
        CommandType commandType;
        if (method.isAnnotationPresent(PatcherHandler.class)) {
            PatcherHandler patcherHandlerAnnotation = method.getAnnotation(PatcherHandler.class);
            path = patcherHandlerAnnotation.path();
            commandType = patcherHandlerAnnotation.action();
        } else if (method.isAnnotationPresent(AddHandler.class)) {
            path = method.getAnnotation(AddHandler.class).value();
            commandType = CommandType.ADD;
        } else if (method.isAnnotationPresent(RemoveHandler.class)) {
            path = method.getAnnotation(RemoveHandler.class).value();
            commandType = CommandType.REMOVE;
        } else if (method.isAnnotationPresent(ReplaceHandler.class)) {
            path = method.getAnnotation(ReplaceHandler.class).value();
            commandType = CommandType.REPLACE;
        } else {
            throw new IllegalArgumentException("Provided method is not annotated properly.");
        }
        return HandlerConfiguration.of(method, path, commandType);
    }

    public PathPattern extractClassPrefix(Class<?> clazz) {
        PatcherConfiguration prefixAnnotation = clazz.getDeclaredAnnotation(PatcherConfiguration.class);
        return extractPath(prefixAnnotation);
    }

    private PathPattern extractPath(PatcherConfiguration patcherConfiguration) {
        String path;
        if (patcherConfiguration == null) {
            path = "";
        } else if (!patcherConfiguration.path().isEmpty()) {
            path = patcherConfiguration.path();
        } else {
            path = patcherConfiguration.value();
        }
        return PathPattern.parse(path);
    }

}
