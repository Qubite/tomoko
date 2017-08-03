package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.handler.invocation.ClientCodeInvocations;
import io.qubite.tomoko.handler.value.ParameterListValueHandler;
import io.qubite.tomoko.handler.value.converter.ValueConverter;
import io.qubite.tomoko.handler.value.converter.ValueConverterFactory;
import io.qubite.tomoko.handler.valueless.ParameterListValuelessHandler;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.path.parameter.StringPathParameter;
import io.qubite.tomoko.path.parameter.TypedPathParameter;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.annotation.*;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;

public class ClassScanner {

    private static final List<Class<? extends Annotation>> HANDLER_ANNOTATIONS = Arrays.asList(PatcherHandler.class, AddHandler.class, RemoveHandler.class, ReplaceHandler.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassScanner.class);

    private final ConfigurationExtractor configurationExtractor = ConfigurationExtractor.instance();

    private final ValueConverterFactory valueConverterFactory;

    ClassScanner(ValueConverterFactory valueConverterFactory) {
        this.valueConverterFactory = valueConverterFactory;
    }

    public static ClassScanner instance(ValueConverterFactory valueConverterFactory) {
        return new ClassScanner(valueConverterFactory);
    }

    public PatcherTreeSpecification build(Object specification) {
        LOGGER.info("Patch specification started");
        PatcherTreeSpecificationBuilder builder = PatcherTreeSpecification.builder();
        return scanClass(builder, PathPattern.empty(), specification).build();
    }

    private PatcherTreeSpecificationBuilder scanClass(PatcherTreeSpecificationBuilder builder, PathPattern prefix, Object specification) {
        if (specification == null) {
            throw new ConfigurationException("Object to be scanned is null");
        }
        Class<?> specificationClass = specification.getClass();
        LOGGER.info("Scanning class {}", specificationClass.getName());
        PathPattern classPrefixPathTemplate = configurationExtractor.extractClassPrefix(specificationClass);
        PathPattern totalPrefix = prefix.append(classPrefixPathTemplate);
        registerMethods(builder, totalPrefix, specification);
        registerLinkedSpecifications(builder, totalPrefix, specification);
        return builder;
    }

    private PatcherTreeSpecificationBuilder registerMethods(PatcherTreeSpecificationBuilder builder, PathPattern prefix, Object specification) {
        Class<?> specificationClass = specification.getClass();
        Method[] publicMethods = specificationClass.getDeclaredMethods();
        for (Method method : publicMethods) {
            if (isPatcherOperation(method)) {
                registerMethod(builder, prefix, specification, method);
            }
        }
        return builder;
    }

    private PatcherTreeSpecificationBuilder registerLinkedSpecifications(PatcherTreeSpecificationBuilder builder, PathPattern prefix, Object specification) {
        Class<?> specificationClass = specification.getClass();
        Method[] publicMethods = specificationClass.getDeclaredMethods();
        for (Method method : publicMethods) {
            if (method.isAnnotationPresent(LinkedConfiguration.class)) {
                checkLinkedSpecification(method);
                LinkedConfiguration annotation = method.getAnnotation(LinkedConfiguration.class);
                PathPattern totalPrefix = prefix.append(PathPattern.parse(annotation.path()));
                LOGGER.info("Found linked configuration method {}.{}() with path {}", method.getDeclaringClass().getSimpleName(), method.getName(), totalPrefix.toString());
                Object linkedSpecificationInstance = getInstance(method, specification);
                scanClass(builder, totalPrefix, linkedSpecificationInstance);
            }
        }
        for (Field field : specificationClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(LinkedConfiguration.class)) {
                checkLinkedSpecification(field);
                LinkedConfiguration annotation = field.getAnnotation(LinkedConfiguration.class);
                PathPattern totalPrefix = prefix.append(PathPattern.parse(annotation.path()));
                LOGGER.info("Found linked configuration field at {}.{} with path {}", field.getDeclaringClass().getSimpleName(), field.getName(), totalPrefix.toString());
                Object linkedSpecificationInstance = getInstance(field, specification);
                scanClass(builder, totalPrefix, linkedSpecificationInstance);
            }
        }
        return builder;
    }

    private Object getInstance(Method method, Object specification) {
        try {
            Object linkedSpecificationInstance = method.invoke(specification);
            if (linkedSpecificationInstance == null) {
                throw new ConfigurationException("Linked configuration object is null at method " + method.getDeclaringClass().getSimpleName() + "::" + method.getName());
            }
            return linkedSpecificationInstance;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ConfigurationException(e);
        }
    }

    private Object getInstance(Field field, Object specification) {
        try {
            field.setAccessible(true);
            Object linkedSpecificationInstance = field.get(specification);
            if (linkedSpecificationInstance == null) {
                throw new ConfigurationException("Linked configuration object is null at field" + field.getDeclaringClass().getSimpleName() + "::" + field.getName());
            }
            return linkedSpecificationInstance;
        } catch (IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }

    private PatcherTreeSpecificationBuilder registerMethod(PatcherTreeSpecificationBuilder builder, PathPattern prefix, Object specification, Method method) {
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        PathPattern handlerPath = prefix.append(PathPattern.parse(handlerConfiguration.getPath()));
        LOGGER.info("Registering {} handler {}::{} at {}", handlerConfiguration.getCommandType(), method.getDeclaringClass().getSimpleName(), method.getName(), handlerPath);
        PathTemplate pathTemplate = PathTemplate.from(handlerPath);
        CommandType operationType = handlerConfiguration.getCommandType();
        if (operationType == CommandType.REMOVE) {
            ParameterListValuelessHandler handler = toValuelessHandler(handlerPath, method, specification);
            builder.handleRemove(pathTemplate, handler);
        } else if (operationType == CommandType.ADD) {
            ParameterListValueHandler handler = toValueHandler(handlerPath, method, specification);
            builder.handleAdd(pathTemplate, handler);
        } else if (operationType == CommandType.REPLACE) {
            ParameterListValueHandler handler = toValueHandler(handlerPath, method, specification);
            builder.handleReplace(pathTemplate, handler);
        }
        return builder;
    }

    private ParameterListValuelessHandler toValuelessHandler(PathPattern handlerPath, Method method, Object instance) {
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        MethodHandle methodHandle = createMethodHandle(method, instance);
        List<PathParameter<?>> pathParameters = parseParameters(parameters, parameters.length, handlerPath);
        ParameterListValuelessHandler handler = ParameterListValuelessHandler.of(pathParameters, ClientCodeInvocations.invocation(methodHandle));
        return handler;
    }

    private ParameterListValueHandler toValueHandler(PathPattern handlerPath, Method method, Object instance) {
        checkValueHandlerMethod(method);
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        MethodHandle methodHandle = createMethodHandle(method, instance);
        List<PathParameter<?>> pathParameters = parseParameters(parameters, parameters.length - 1, handlerPath);
        ValueType<?> valueType = extractValueType(parameters[parameters.length - 1]);
        ValueConverter<?> converter = valueConverterFactory.forType(valueType);
        ParameterListValueHandler handler = ParameterListValueHandler.of(pathParameters, converter, ClientCodeInvocations.invocation(methodHandle));
        return handler;
    }

    private void checkValueHandlerMethod(Method method) {
        if (method.getParameterCount() == 0) {
            throw new ConfigurationException("Method marked as an add/replace handler must contain at least one argument.");
        }
    }

    private MethodHandle createMethodHandle(Method method, Object instance) {
        try {
            MethodHandle methodHandle = MethodHandles.lookup().unreflect(method);
            methodHandle = methodHandle.bindTo(instance);
            methodHandle = methodHandle.asType(MethodType.methodType(void.class, method.getParameterTypes()));
            methodHandle = methodHandle.asSpreader(Object[].class, method.getParameterCount());
            CallSite callSiteMethod = new ConstantCallSite(methodHandle);
            methodHandle = callSiteMethod.dynamicInvoker();
            return methodHandle;
        } catch (IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }

    private List<PathParameter<?>> parseParameters(java.lang.reflect.Parameter[] parameters, int length, PathPattern pattern) {
        List<PathParameter<?>> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(toPathParameter(parameters[i], pattern));
        }
        return result;
    }

    private PathParameter<?> toPathParameter(java.lang.reflect.Parameter parameter, PathPattern pattern) {
        String parameterName = configurationExtractor.extractParameterName(parameter);
        int elementIndex;
        try {
            elementIndex = pattern.getElementIndexByParameterName(parameterName);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Parameter " + parameterName + " does not exist on the path.");
        }
        StringPathParameter baseParameter = StringPathParameter.of(elementIndex);
        return TypedPathParameter.of(baseParameter, configurationExtractor.getDefaultConverter(parameter));
    }

    private ValueType<?> extractValueType(java.lang.reflect.Parameter parameter) {
        Type type = parameter.getParameterizedType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parametrizedType = (ParameterizedType) type;
            Class<?> containerType = (Class<?>) parametrizedType.getRawType();
            if (Collection.class.isAssignableFrom(containerType)) {
                Type elementType = parametrizedType.getActualTypeArguments()[0];
                Class<?> elementClass = extractRawClass(elementType);
                return Types.generic(containerType, elementClass);
            } else if (Map.class.isAssignableFrom(containerType)) {
                Type keyType = parametrizedType.getActualTypeArguments()[0];
                Class<?> keyClass = extractRawClass(keyType);
                Type valueType = parametrizedType.getActualTypeArguments()[0];
                Class<?> valueClass = extractRawClass(valueType);
                return Types.generic(containerType, keyClass, valueClass);
            } else {
                throw new ConfigurationException("Unsupported value type");
            }
        } else if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            return Types.simple(clazz);
        } else {
            throw new IllegalArgumentException("Parameter type is neither ParametrizedType nor Class");
        }
    }

    private Class<?> extractRawClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException("Element type is neither ParametrizedType nor Class");
        }
    }

    private boolean isPatcherOperation(Method method) {
        boolean isAnnotationPresent = HANDLER_ANNOTATIONS.stream().anyMatch((annotationType) -> method.isAnnotationPresent(annotationType));
        return Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()) && isAnnotationPresent;
    }

    private void checkLinkedSpecification(Field field) {
        boolean check = !Modifier.isStatic(field.getModifiers());
        if (!check) {
            throw new ConfigurationException(String.format("Field %s::%s is marked with @LinkedConfiguration but is static.", field.getDeclaringClass().getSimpleName(), field.getName()));
        }
    }

    private void checkLinkedSpecification(Method method) {
        boolean check = Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers());
        if (!check) {
            throw new ConfigurationException(String.format("Method %s::%s is marked with @LinkedConfiguration but is not public or is static.", method.getDeclaringClass().getSimpleName(), method.getName()));
        }
        if (method.getParameterCount() != 0) {
            throw new ConfigurationException(String.format("Method %s::%s is marked with @LinkedConfiguration but is not a parameterless getter.", method.getDeclaringClass().getSimpleName(), method.getName()));
        }
    }

}
