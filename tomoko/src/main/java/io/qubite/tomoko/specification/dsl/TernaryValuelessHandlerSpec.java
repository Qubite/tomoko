package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.configuration.ParameterConfiguration;
import io.qubite.tomoko.configuration.PathParameterFactory;
import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.converter.PathParameterConverter;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.valueless.TernaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.TriConsumer;
import net.jodah.typetools.TypeResolver;

public class TernaryValuelessHandlerSpec<A, B, C> {

    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final PathPattern pathPattern;
    private final TriConsumer<A, B, C> handler;

    private final ParameterConfiguration<A> firstParameterOverride;
    private final ParameterConfiguration<B> secondParameterOverride;
    private final ParameterConfiguration<C> thirdParameterOverride;

    TernaryValuelessHandlerSpec(PathPattern pathPattern, TriConsumer<A, B, C> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ParameterConfiguration<A> firstParameterOverride, ParameterConfiguration<B> secondParameterOverride, ParameterConfiguration<C> thirdParameterOverride) {
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.firstParameterOverride = firstParameterOverride;
        this.secondParameterOverride = secondParameterOverride;
        this.thirdParameterOverride = thirdParameterOverride;
    }

    public static <A, B, C> TernaryValuelessHandlerSpec<A, B, C> of(PathPattern pathPattern, TriConsumer<A, B, C> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, null, null, null);
    }

    public TernaryValuelessHandlerSpec<A, B, C> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration<A> parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, parameter, secondParameterOverride, thirdParameterOverride);
    }

    public TernaryValuelessHandlerSpec<A, B, C> firstArgument(String name) {
        Class<A> parameterClass = (Class<A>) TypeResolver.resolveRawArguments(TriConsumer.class, handler.getClass())[0];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    public TernaryValuelessHandlerSpec<A, B, C> secondArgument(String name, PathParameterConverter<B> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration<B> parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, firstParameterOverride, parameter, thirdParameterOverride);
    }

    public TernaryValuelessHandlerSpec<A, B, C> secondArgument(String name) {
        Class<B> parameterClass = (Class<B>) TypeResolver.resolveRawArguments(TriConsumer.class, handler.getClass())[1];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> secondArgument(String name, Class<B> argumentType) {
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    public TernaryValuelessHandlerSpec<A, B, C> thirdArgument(String name, PathParameterConverter<C> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration<C> parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, firstParameterOverride, secondParameterOverride, parameter);
    }

    public TernaryValuelessHandlerSpec<A, B, C> thirdArgument(String name) {
        Class<C> parameterClass = (Class<C>) TypeResolver.resolveRawArguments(TriConsumer.class, handler.getClass())[2];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return thirdArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> thirdArgument(String name, Class<C> argumentType) {
        return thirdArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock or the type is generic.
     * In that case the type should be specified through the value() method.
     */
    public TernaryValuelessHandlerDescriptor<A, B, C> register() {
        if (firstParameterOverride == null) {
            throw new ConfigurationException("First parameter has not been described. Use appropriate DSL methods.");
        }
        if (secondParameterOverride == null) {
            throw new ConfigurationException("Second parameter has not been described. Use appropriate DSL methods.");
        }
        if (thirdParameterOverride == null) {
            throw new ConfigurationException("Third parameter has not been described. Use appropriate DSL methods.");
        }
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        PathParameter<B> secondParameter = PathParameterFactory.instance().toPathParameter(secondParameterOverride, pathPattern);
        PathParameter<C> thirdParameter = PathParameterFactory.instance().toPathParameter(thirdParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        ParameterDescriptor<B> secondParameterDescriptor = ParameterDescriptor.of(secondParameterOverride.getParameterName(), secondParameterOverride.getConverter());
        ParameterDescriptor<C> thirdParameterDescriptor = ParameterDescriptor.of(thirdParameterOverride.getParameterName(), thirdParameterOverride.getConverter());
        builder.handleRemove(PathTemplate.from(pathPattern), handlerFactory.handler(firstParameter, secondParameter, thirdParameter, handler));
        return new TernaryValuelessHandlerDescriptor<>(pathPattern, firstParameterDescriptor, secondParameterDescriptor, thirdParameterDescriptor);
    }

}
