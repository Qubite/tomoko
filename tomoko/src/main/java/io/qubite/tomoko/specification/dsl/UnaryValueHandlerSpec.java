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
import io.qubite.tomoko.specification.descriptor.value.UnaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.type.TypeExtractor;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;
import net.jodah.typetools.TypeResolver;

import java.util.function.BiConsumer;

public class UnaryValueHandlerSpec<A, V> {

    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final CommandType commandType;
    private final PathPattern pathPattern;
    private final BiConsumer<A, V> handler;

    private final ParameterConfiguration<A> firstParameterOverride;
    private final ValueType<V> valueTypeOverride;


    UnaryValueHandlerSpec(CommandType commandType, PathPattern pathPattern, BiConsumer<A, V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ParameterConfiguration<A> firstParameterOverride, ValueType<V> valueTypeOverride) {
        this.commandType = commandType;
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.firstParameterOverride = firstParameterOverride;
        this.valueTypeOverride = valueTypeOverride;
    }

    public static <A, V> UnaryValueHandlerSpec<A, V> of(CommandType commandType, PathPattern pathPattern, BiConsumer<A, V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new UnaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, null, null);
    }

    /**
     * Use Types class to override the extracted value type.
     *
     * @param valueType
     * @return
     */
    public UnaryValueHandlerSpec<A, V> type(ValueType<V> valueType) {
        return new UnaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, valueType);
    }

    public UnaryValueHandlerSpec<A, V> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new UnaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, parameter, valueTypeOverride);
    }

    public UnaryValueHandlerSpec<A, V> firstArgument(String name) {
        Class<A> parameterClass = (Class<A>) TypeResolver.resolveRawArguments(BiConsumer.class, handler.getClass())[0];
        if (parameterClass.equals(TypeResolver.Unknown.class)) {
            throw new ConfigurationException("Parameter type cannot be infered. Set converter directly.");
        }
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public UnaryValueHandlerSpec<A, V> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock or the type is generic.
     * In that case the type should be specified through the value() method.
     */
    public UnaryValueHandlerDescriptor<A, V> register() {
        if (firstParameterOverride == null) {
            throw new ConfigurationException("First parameter has not been described. Use appropriate DSL methods.");
        }
        ValueType<V> finalType = valueTypeOverride == null ? TypeExtractor.extractSimpleType(handler) : valueTypeOverride;
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        if (commandType == CommandType.ADD) {
            builder.handleAdd(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, firstParameter, handler));
        } else {
            builder.handleReplace(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, firstParameter, handler));
        }
        return new UnaryValueHandlerDescriptor<>(commandType, pathPattern, firstParameterDescriptor);
    }

}
