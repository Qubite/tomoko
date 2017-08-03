package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.configuration.ParameterConfiguration;
import io.qubite.tomoko.configuration.PathParameterFactory;
import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.path.converter.PathParameterConverter;
import io.qubite.tomoko.path.parameter.PathParameter;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.value.BinaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.type.TypeExtractor;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.TriConsumer;
import net.jodah.typetools.TypeResolver;

public class BinaryValueHandlerSpec<A, B, V> {

    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final CommandType commandType;
    private final PathPattern pathPattern;
    private final TriConsumer<A, B, V> handler;

    private final ParameterConfiguration<A> firstParameterOverride;
    private final ParameterConfiguration<B> secondParameterOverride;
    private final ValueType<V> valueTypeOverride;


    BinaryValueHandlerSpec(CommandType commandType, PathPattern pathPattern, TriConsumer<A, B, V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ParameterConfiguration<A> firstParameterOverride, ParameterConfiguration<B> secondParameterOverride, ValueType<V> valueTypeOverride) {
        this.commandType = commandType;
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.firstParameterOverride = firstParameterOverride;
        this.secondParameterOverride = secondParameterOverride;
        this.valueTypeOverride = valueTypeOverride;
    }

    public static <A, B, V> BinaryValueHandlerSpec<A, B, V> of(CommandType commandType, PathPattern pathPattern, TriConsumer<A, B, V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, null, null, null);
    }

    /**
     * Use Types class to override the extracted value type.
     *
     * @param valueType
     * @return
     */
    public BinaryValueHandlerSpec<A, B, V> type(ValueType<V> valueType) {
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, secondParameterOverride, valueType);
    }

    public BinaryValueHandlerSpec<A, B, V> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, parameter, secondParameterOverride, valueTypeOverride);
    }

    public BinaryValueHandlerSpec<A, B, V> firstArgument(String name) {
        Class<A> parameterClass = (Class<A>) TypeResolver.resolveRawArguments(TriConsumer.class, handler.getClass())[0];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValueHandlerSpec<A, B, V> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    public BinaryValueHandlerSpec<A, B, V> secondArgument(String name, PathParameterConverter<B> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, parameter, valueTypeOverride);
    }

    public BinaryValueHandlerSpec<A, B, V> secondArgument(String name) {
        Class<B> parameterClass = (Class<B>) TypeResolver.resolveRawArguments(TriConsumer.class, handler.getClass())[1];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValueHandlerSpec<A, B, V> secondArgument(String name, Class<B> argumentType) {
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock or the type is generic.
     * In that case the type should be specified through the value() method.
     */
    public BinaryValueHandlerDescriptor<A, B, V> register() {
        if (firstParameterOverride == null) {
            throw new ConfigurationException("First parameter has not been described. Use appropriate DSL methods.");
        }
        if (secondParameterOverride == null) {
            throw new ConfigurationException("Second parameter has not been described. Use appropriate DSL methods.");
        }
        ValueType<V> finalType = valueTypeOverride == null ? TypeExtractor.extractSimpleType(handler) : valueTypeOverride;
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        PathParameter<B> secondParameter = PathParameterFactory.instance().toPathParameter(secondParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        ParameterDescriptor<B> secondParameterDescriptor = ParameterDescriptor.of(secondParameterOverride.getParameterName(), secondParameterOverride.getConverter());
        if (commandType == CommandType.ADD) {
            builder.handleAdd(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, firstParameter, secondParameter, handler));
        } else {
            builder.handleReplace(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, firstParameter, secondParameter, handler));
        }
        return new BinaryValueHandlerDescriptor<>(commandType, pathPattern, firstParameterDescriptor, secondParameterDescriptor);
    }

}
