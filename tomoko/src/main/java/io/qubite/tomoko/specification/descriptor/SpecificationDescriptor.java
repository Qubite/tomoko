package io.qubite.tomoko.specification.descriptor;

import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.specification.annotation.LinkedConfiguration;
import io.qubite.tomoko.specification.descriptor.value.BinaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.value.NullaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.value.TernaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.value.UnaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.valueless.BinaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.valueless.NullaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.valueless.TernaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.descriptor.valueless.UnaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.HandlerConfiguration;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.QuinConsumer;
import io.qubite.tomoko.util.TriConsumer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Type-safe way of generating paths and operations according to annotated specification class.
 *
 * @param <T>
 */
public class SpecificationDescriptor<T> {

    private final ConfigurationExtractor configurationExtractor = ConfigurationExtractor.instance();
    private final MethodProxy<T> methodProxy;
    private final PathPattern prefix;

    public SpecificationDescriptor(MethodProxy<T> methodProxy, PathPattern prefix) {
        this.methodProxy = methodProxy;
        this.prefix = prefix;
    }

    public static <T> SpecificationDescriptor<T> forClass(Class<T> clazz) {
        return forClass(clazz, PathPattern.empty());
    }

    private static <T> SpecificationDescriptor<T> forClass(Class<T> clazz, PathPattern prefix) {
        return new SpecificationDescriptor<>(MethodProxy.forClass(clazz), prefix.append(ConfigurationExtractor.instance().extractClassPrefix(clazz)));
    }

    public <C> SpecificationDescriptor<C> linked(Function<T, C> getter) {
        Method method = methodProxy.getMethod(getter);
        Preconditions.checkArgument(method.isAnnotationPresent(LinkedConfiguration.class), "Method not marked as linked configuration.");
        LinkedConfiguration annotation = method.getAnnotation(LinkedConfiguration.class);
        Class<C> linkedConfigurationClass = (Class<C>) method.getReturnType();
        PathPattern linkPrefix = PathPattern.parse(annotation.path());
        return forClass(linkedConfigurationClass, linkPrefix);
    }

    public <V> NullaryValueHandlerDescriptor<V> addHandler(BiConsumer<T, V> handler) {
        return valueHandler(CommandType.ADD, handler);
    }

    public <A, V> UnaryValueHandlerDescriptor<A, V> addHandler(TriConsumer<T, A, V> handler) {
        return valueHandler(CommandType.ADD, handler);
    }

    public <A, B, V> BinaryValueHandlerDescriptor<A, B, V> addHandler(QuadConsumer<T, A, B, V> handler) {
        return valueHandler(CommandType.ADD, handler);
    }

    public <A, B, C, V> TernaryValueHandlerDescriptor<A, B, C, V> addHandler(QuinConsumer<T, A, B, C, V> handler) {
        return valueHandler(CommandType.ADD, handler);
    }

    public <V> NullaryValueHandlerDescriptor<V> replaceHandler(BiConsumer<T, V> handler) {
        return valueHandler(CommandType.REPLACE, handler);
    }

    public <A, V> UnaryValueHandlerDescriptor<A, V> replaceHandler(TriConsumer<T, A, V> handler) {
        return valueHandler(CommandType.REPLACE, handler);
    }

    public <A, B, V> BinaryValueHandlerDescriptor<A, B, V> replaceHandler(QuadConsumer<T, A, B, V> handler) {
        return valueHandler(CommandType.REPLACE, handler);
    }

    public <A, B, C, V> TernaryValueHandlerDescriptor<A, B, C, V> replaceHandler(QuinConsumer<T, A, B, C, V> handler) {
        return valueHandler(CommandType.REPLACE, handler);
    }

    private <V> NullaryValueHandlerDescriptor<V> valueHandler(CommandType type, BiConsumer<T, V> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, type);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        return new NullaryValueHandlerDescriptor<>(type, handlerPath);
    }

    private <A, V> UnaryValueHandlerDescriptor<A, V> valueHandler(CommandType type, TriConsumer<T, A, V> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, type);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        Parameter[] parameters = method.getParameters();
        ParameterDescriptor<A> firstParameterDescriptor = (ParameterDescriptor<A>) configurationExtractor.extractParameter(parameters[0]);
        return new UnaryValueHandlerDescriptor<>(type, handlerPath, firstParameterDescriptor);
    }

    private <A, B, V> BinaryValueHandlerDescriptor<A, B, V> valueHandler(CommandType type, QuadConsumer<T, A, B, V> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, type);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        Parameter[] parameters = method.getParameters();
        ParameterDescriptor<A> firstParameterDescriptor = (ParameterDescriptor<A>) configurationExtractor.extractParameter(parameters[0]);
        ParameterDescriptor<B> secondParameterDescriptor = (ParameterDescriptor<B>) configurationExtractor.extractParameter(parameters[1]);
        return new BinaryValueHandlerDescriptor<>(type, handlerPath, firstParameterDescriptor, secondParameterDescriptor);
    }

    private <A, B, C, V> TernaryValueHandlerDescriptor<A, B, C, V> valueHandler(CommandType type, QuinConsumer<T, A, B, C, V> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, type);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        Parameter[] parameters = method.getParameters();
        ParameterDescriptor<A> firstParameterDescriptor = (ParameterDescriptor<A>) configurationExtractor.extractParameter(parameters[0]);
        ParameterDescriptor<B> secondParameterDescriptor = (ParameterDescriptor<B>) configurationExtractor.extractParameter(parameters[1]);
        ParameterDescriptor<C> thirdParameterDescriptor = (ParameterDescriptor<C>) configurationExtractor.extractParameter(parameters[2]);
        return new TernaryValueHandlerDescriptor<>(type, handlerPath, firstParameterDescriptor, secondParameterDescriptor, thirdParameterDescriptor);
    }

    public NullaryValuelessHandlerDescriptor removeHandler(Consumer<T> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, CommandType.REMOVE);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        return new NullaryValuelessHandlerDescriptor(handlerPath);
    }

    public <A> UnaryValuelessHandlerDescriptor<A> removeHandler(BiConsumer<T, A> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, CommandType.REMOVE);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        Parameter[] parameters = method.getParameters();
        ParameterDescriptor<A> firstParameterDescriptor = (ParameterDescriptor<A>) configurationExtractor.extractParameter(parameters[0]);
        return new UnaryValuelessHandlerDescriptor<>(handlerPath, firstParameterDescriptor);
    }

    public <A, B> BinaryValuelessHandlerDescriptor<A, B> removeHandler(TriConsumer<T, A, B> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, CommandType.REMOVE);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        Parameter[] parameters = method.getParameters();
        ParameterDescriptor<A> firstParameterDescriptor = (ParameterDescriptor<A>) configurationExtractor.extractParameter(parameters[0]);
        ParameterDescriptor<B> secondParameterDescriptor = (ParameterDescriptor<B>) configurationExtractor.extractParameter(parameters[1]);
        return new BinaryValuelessHandlerDescriptor<>(handlerPath, firstParameterDescriptor, secondParameterDescriptor);
    }

    public <A, B, C> TernaryValuelessHandlerDescriptor<A, B, C> removeHandler(QuadConsumer<T, A, B, C> handler) {
        Method method = methodProxy.getMethod(handler);
        HandlerConfiguration handlerConfiguration = configurationExtractor.extractHandlerConfiguration(method);
        checkHandlerType(handlerConfiguration, CommandType.REMOVE);
        PathPattern handlerPath = getHandlerPath(handlerConfiguration);
        Parameter[] parameters = method.getParameters();
        ParameterDescriptor<A> firstParameterDescriptor = (ParameterDescriptor<A>) configurationExtractor.extractParameter(parameters[0]);
        ParameterDescriptor<B> secondParameterDescriptor = (ParameterDescriptor<B>) configurationExtractor.extractParameter(parameters[1]);
        ParameterDescriptor<C> thirdParameterDescriptor = (ParameterDescriptor<C>) configurationExtractor.extractParameter(parameters[2]);
        return new TernaryValuelessHandlerDescriptor<>(handlerPath, firstParameterDescriptor, secondParameterDescriptor, thirdParameterDescriptor);
    }

    private PathPattern getHandlerPath(HandlerConfiguration handlerConfiguration) {
        PathPattern handlerPrefix = PathPattern.parse(handlerConfiguration.getPath());
        return prefix.append(handlerPrefix);
    }

    private void checkHandlerType(HandlerConfiguration configuration, CommandType expectedType) {
        if (!configuration.getCommandType().equals(expectedType)) {
            throw new TomokoException("Method " + configuration.getMethod().getDeclaringClass().getSimpleName() + "::" + configuration.getMethod().getName() + " is not a handler for " + expectedType.name() + " operation.");
        }
    }

}
