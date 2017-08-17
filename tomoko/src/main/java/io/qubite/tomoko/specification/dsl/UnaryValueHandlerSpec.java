package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.configuration.LambdaDescriptor;
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
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;

import java.util.function.BiConsumer;

/**
 * Handler configuration phase DSL. For more information check {@link io.qubite.tomoko.specification.dsl}.
 *
 * @param <A> handler first parameter type
 * @param <V> handler value type
 */
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
     * Use {@link Types} class to override the extracted value type. Must be used if the type cannot be infered. This can happen when the handler is a mock/proxy or the type is generic.
     *
     * @param valueType
     * @return ongoing handler configuration
     */
    public UnaryValueHandlerSpec<A, V> value(ValueType<V> valueType) {
        return new UnaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, valueType);
    }

    /**
     * Shorthand for value(Types.simple(valueRawClass)).
     *
     * @param valueRawClass
     * @return
     */
    public UnaryValueHandlerSpec<A, V> simpleValue(Class<V> valueRawClass) {
        return value(Types.simple(valueRawClass));
    }

    public UnaryValueHandlerSpec<A, V> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new UnaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, parameter, valueTypeOverride);
    }

    public UnaryValueHandlerSpec<A, V> firstArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public UnaryValueHandlerSpec<A, V> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private UnaryValueHandlerSpec<A, V> inferFirstArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(lambda.extractName(0), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    /**
     * Completes the path template definition and registers a handler.<br/>
     * <br/>
     * If parameter name/type cannot be infered then use *Argument method to describe them explicitly.<br/>
     * <br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock/proxy or the type is generic.
     * In that case the type should be specified through the {@link #value(ValueType)} method.<br/>
     * <br/>
     * Ends the handler configuration phase.
     *
     * @return path/handler descriptor
     */
    public UnaryValueHandlerDescriptor<A, V> register() {
        UnaryValueHandlerSpec<A, V> spec = this;
        if (firstParameterOverride == null) {
            spec = spec.inferFirstArgument();
        }
        return spec.internalRegister();
    }

    private UnaryValueHandlerDescriptor<A, V> internalRegister() {
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
