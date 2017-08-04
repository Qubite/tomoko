package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.configuration.LambdaDescriptor;
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

/**
 * Handler configuration phase DSL. For more information check {@link io.qubite.tomoko.specification.dsl}.
 *
 * @param <A> handler first parameter type
 * @param <B> handler second parameter type
 * @param <C> handler third parameter type
 */
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
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, parameter, secondParameterOverride, thirdParameterOverride);
    }

    public TernaryValuelessHandlerSpec<A, B, C> firstArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private TernaryValuelessHandlerSpec<A, B, C> inferFirstArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(lambda.extractName(0), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> secondArgument(String name, PathParameterConverter<B> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, firstParameterOverride, parameter, thirdParameterOverride);
    }

    public TernaryValuelessHandlerSpec<A, B, C> secondArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<B> parameterClass = (Class<B>) lambda.extractParameterClass(1);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> secondArgument(String name, Class<B> argumentType) {
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private TernaryValuelessHandlerSpec<A, B, C> inferSecondArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<B> parameterClass = (Class<B>) lambda.extractParameterClass(1);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return secondArgument(lambda.extractName(1), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> thirdArgument(String name, PathParameterConverter<C> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, firstParameterOverride, secondParameterOverride, parameter);
    }

    public TernaryValuelessHandlerSpec<A, B, C> thirdArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<C> parameterClass = (Class<C>) lambda.extractParameterClass(2);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return thirdArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValuelessHandlerSpec<A, B, C> thirdArgument(String name, Class<C> argumentType) {
        return thirdArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private TernaryValuelessHandlerSpec<A, B, C> inferThirdArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<C> parameterClass = (Class<C>) lambda.extractParameterClass(2);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return thirdArgument(lambda.extractName(2), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     */
    public TernaryValuelessHandlerDescriptor<A, B, C> register() {
        TernaryValuelessHandlerSpec<A, B, C> spec = this;
        if (firstParameterOverride == null) {
            spec = spec.inferFirstArgument();
        }
        if (secondParameterOverride == null) {
            spec = spec.inferSecondArgument();
        }
        if (thirdParameterOverride == null) {
            spec = spec.inferThirdArgument();
        }
        return spec.internalRegister();
    }

    private TernaryValuelessHandlerDescriptor<A, B, C> internalRegister() {
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
