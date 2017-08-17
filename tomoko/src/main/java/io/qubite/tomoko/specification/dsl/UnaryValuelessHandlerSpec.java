package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.configuration.LambdaDescriptor;
import io.qubite.tomoko.configuration.ParameterConfiguration;
import io.qubite.tomoko.configuration.PathParameterFactory;
import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.converter.PathParameterConverter;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.valueless.UnaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.util.Preconditions;

import java.util.function.Consumer;

public class UnaryValuelessHandlerSpec<A> {

    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final PathPattern pathPattern;
    private final Consumer<A> handler;

    private final ParameterConfiguration<A> firstParameterOverride;

    UnaryValuelessHandlerSpec(PathPattern pathPattern, Consumer<A> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ParameterConfiguration<A> firstParameterOverride) {
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.firstParameterOverride = firstParameterOverride;
    }

    public static <A> UnaryValuelessHandlerSpec<A> of(PathPattern pathPattern, Consumer<A> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new UnaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, null);
    }

    public UnaryValuelessHandlerSpec<A> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new UnaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, parameter);
    }

    public UnaryValuelessHandlerSpec<A> firstArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public UnaryValuelessHandlerSpec<A> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private UnaryValuelessHandlerSpec<A> inferFirstArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(lambda.extractName(0), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     */
    public UnaryValuelessHandlerDescriptor<A> register() {
        UnaryValuelessHandlerSpec<A> spec = this;
        if (firstParameterOverride == null) {
            spec = spec.inferFirstArgument();
        }
        return spec.internalRegister();
    }

    private UnaryValuelessHandlerDescriptor<A> internalRegister() {
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        builder.handleRemove(PathTemplate.from(pathPattern), handlerFactory.handler(firstParameter, handler));
        return new UnaryValuelessHandlerDescriptor<A>(pathPattern, firstParameterDescriptor);
    }

}
