package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.configuration.ConfigurationException;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.path.converter.Converters;
import io.qubite.tomoko.path.converter.IdentityConverter;
import io.qubite.tomoko.path.converter.PathParameterConverter;
import io.qubite.tomoko.specification.annotation.*;

import java.lang.reflect.Method;

public class ConfigurationExtractor {

    ConfigurationExtractor() {
    }

    public static ConfigurationExtractor instance() {
        return new ConfigurationExtractor();
    }

    public PathParameterConverter<?> getDefaultConverter(java.lang.reflect.Parameter parameter) {
        Class<?> parameterClass = parameter.getType();
        return getDefaultConverter(parameterClass);
    }

    public <T> PathParameterConverter<T> getDefaultConverter(Class<T> targetType) {
        if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
            return (PathParameterConverter<T>) Converters.integer();
        } else if (targetType.equals(long.class) || targetType.equals(Long.class)) {
            return (PathParameterConverter<T>) Converters.longConverter();
        } else if (targetType.equals(String.class)) {
            return (PathParameterConverter<T>) Converters.identity();
        } else {
            throw new ConfigurationException("No converter known for type " + targetType.getSimpleName());
        }
    }

    private PathParameterConverter<?> extractConverter(java.lang.reflect.Parameter parameter) {
        if (parameter.isAnnotationPresent(Parameter.class)) {
            Class<? extends PathParameterConverter<?>> annotationConverter = parameter.getAnnotation(Parameter.class).converter();
            if (annotationConverter.equals(IdentityConverter.class)) {
                return getDefaultConverter(parameter);
            } else {
                return createConverter(annotationConverter);
            }
        }
        if (parameter.isAnnotationPresent(UrlEncoded.class)) {
            return Converters.urlEncoded();
        }
        throw new ConfigurationException("Could not determine converter for parameter " + parameter.getName());
    }

    private String extractParameterName(java.lang.reflect.Parameter parameter) {
        if (parameter.isAnnotationPresent(Parameter.class)) {
            Parameter namedParameter = parameter.getAnnotation(Parameter.class);
            if (!namedParameter.name().isEmpty()) {
                return namedParameter.name();
            } else if (!namedParameter.value().isEmpty()) {
                return namedParameter.value();
            }
        }
        if (parameter.isAnnotationPresent(UrlEncoded.class)) {
            if (!parameter.getAnnotation(UrlEncoded.class).value().isEmpty()) {
                return parameter.getAnnotation(UrlEncoded.class).value();
            }
        }
        if (parameter.isNamePresent()) {
            return parameter.getName();
        }
        throw new ConfigurationException("Could not determine the name of a parameter. Either use @Parameter annotation or compile the code with -parameters flag (Java 8+).");
    }

    public ParameterDescriptor<?> extractParameter(java.lang.reflect.Parameter parameter) {
        PathParameterConverter<?> converter = extractConverter(parameter);
        String parameterName = extractParameterName(parameter);
        return ParameterDescriptor.of(parameterName, converter);
    }

    private PathParameterConverter<?> createConverter(Class<? extends PathParameterConverter<?>> converterClass) {
        try {
            return converterClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConfigurationException("Cannot instantiate converter of type " + converterClass.getSimpleName());
        }
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
        } else {
            path = patcherConfiguration.value();
        }
        return PathPattern.parse(path);
    }

}
