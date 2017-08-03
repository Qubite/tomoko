package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.configuration.ParameterConfiguration;
import io.qubite.tomoko.configuration.PathParameterFactory;
import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.converter.PathParameterConverter;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.valueless.BinaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.util.Preconditions;
import net.jodah.typetools.TypeResolver;

import java.util.function.BiConsumer;

public class BinaryValuelessHandlerSpec<A, B> {

    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final PathPattern pathPattern;
    private final BiConsumer<A, B> handler;

    private final ParameterConfiguration<A> firstParameterOverride;
    private final ParameterConfiguration<B> secondParameterOverride;

    BinaryValuelessHandlerSpec(PathPattern pathPattern, BiConsumer<A, B> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ParameterConfiguration<A> firstParameterOverride, ParameterConfiguration<B> secondParameterOverride) {
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.firstParameterOverride = firstParameterOverride;
        this.secondParameterOverride = secondParameterOverride;
    }

    public static <A, B> BinaryValuelessHandlerSpec<A, B> of(PathPattern pathPattern, BiConsumer<A, B> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new BinaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, null, null);
    }

    public BinaryValuelessHandlerSpec<A, B> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration<A> parameter = ParameterConfiguration.of(name, converter);
        return new BinaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, parameter, secondParameterOverride);
    }

    public BinaryValuelessHandlerSpec<A, B> firstArgument(String name) {
        Class<A> parameterClass = (Class<A>) TypeResolver.resolveRawArguments(BiConsumer.class, handler.getClass())[0];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValuelessHandlerSpec<A, B> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    public BinaryValuelessHandlerSpec<A, B> secondArgument(String name, PathParameterConverter<B> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration<B> parameter = ParameterConfiguration.of(name, converter);
        return new BinaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, firstParameterOverride, parameter);
    }

    public BinaryValuelessHandlerSpec<A, B> secondArgument(String name) {
        Class<B> parameterClass = (Class<B>) TypeResolver.resolveRawArguments(BiConsumer.class, handler.getClass())[1];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValuelessHandlerSpec<A, B> secondArgument(String name, Class<B> argumentType) {
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock or the type is generic.
     * In that case the type should be specified through the value() method.
     */
    public BinaryValuelessHandlerDescriptor<A, B> register() {
        if (firstParameterOverride == null) {
            throw new ConfigurationException("First parameter has not been described. Use appropriate DSL methods.");
        }
        if (secondParameterOverride == null) {
            throw new ConfigurationException("Second parameter has not been described. Use appropriate DSL methods.");
        }
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        PathParameter<B> secondParameter = PathParameterFactory.instance().toPathParameter(secondParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        ParameterDescriptor<B> secondParameterDescriptor = ParameterDescriptor.of(secondParameterOverride.getParameterName(), secondParameterOverride.getConverter());
        builder.handleRemove(PathTemplate.from(pathPattern), handlerFactory.handler(firstParameter, secondParameter, handler));
        return new BinaryValuelessHandlerDescriptor<>(pathPattern, firstParameterDescriptor, secondParameterDescriptor);
    }

}
