package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.ConfigurationException;
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
import net.jodah.typetools.TypeResolver;

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
        ParameterConfiguration<A> parameter = ParameterConfiguration.of(name, converter);
        return new UnaryValuelessHandlerSpec<>(pathPattern, handler, builder, handlerFactory, parameter);
    }

    public UnaryValuelessHandlerSpec<A> firstArgument(String name) {
        Class<A> parameterClass = (Class<A>) TypeResolver.resolveRawArguments(Consumer.class, handler.getClass())[0];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public UnaryValuelessHandlerSpec<A> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock or the type is generic.
     * In that case the type should be specified through the value() method.
     */
    public UnaryValuelessHandlerDescriptor<A> register() {
        if (firstParameterOverride == null) {
            throw new ConfigurationException("First parameter has not been described. Use appropriate DSL methods.");
        }
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        builder.handleRemove(PathTemplate.from(pathPattern), handlerFactory.handler(firstParameter, handler));
        return new UnaryValuelessHandlerDescriptor<A>(pathPattern, firstParameterDescriptor);
    }

}
