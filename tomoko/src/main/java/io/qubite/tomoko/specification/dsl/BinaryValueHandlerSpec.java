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
import io.qubite.tomoko.specification.descriptor.value.BinaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.type.TypeExtractor;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.TriConsumer;

/**
 * Handler configuration phase DSL. For more information check {@link io.qubite.tomoko.specification.dsl}.
 *
 * @param <A> handler first parameter type
 * @param <B> handler second parameter type
 * @param <V> handler value type
 */
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
     * Use {@link Types} class to override the extracted value type. Must be used if the type cannot be infered. This can happen when the handler is a mock/proxy or the type is generic.
     *
     * @param valueType
     * @return ongoing handler configuration
     */
    public BinaryValueHandlerSpec<A, B, V> value(ValueType<V> valueType) {
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, secondParameterOverride, valueType);
    }

    /**
     * Shorthand for value(Types.simple(valueRawClass)).
     *
     * @param valueRawClass
     * @return
     */
    public BinaryValueHandlerSpec<A, B, V> simpleValue(Class<V> valueRawClass) {
        return value(Types.simple(valueRawClass));
    }

    public BinaryValueHandlerSpec<A, B, V> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, parameter, secondParameterOverride, valueTypeOverride);
    }

    public BinaryValueHandlerSpec<A, B, V> firstArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValueHandlerSpec<A, B, V> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private BinaryValueHandlerSpec<A, B, V> inferFirstArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(lambda.extractName(0), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValueHandlerSpec<A, B, V> secondArgument(String name, PathParameterConverter<B> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new BinaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, parameter, valueTypeOverride);
    }

    public BinaryValueHandlerSpec<A, B, V> secondArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<B> parameterClass = (Class<B>) lambda.extractParameterClass(1);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public BinaryValueHandlerSpec<A, B, V> secondArgument(String name, Class<B> argumentType) {
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private BinaryValueHandlerSpec<A, B, V> inferSecondArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<B> parameterClass = (Class<B>) lambda.extractParameterClass(1);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return secondArgument(lambda.extractName(1), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
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
    public BinaryValueHandlerDescriptor<A, B, V> register() {
        BinaryValueHandlerSpec<A, B, V> spec = this;
        if (firstParameterOverride == null) {
            spec = spec.inferFirstArgument();
        }
        if (secondParameterOverride == null) {
            spec = spec.inferSecondArgument();
        }
        return spec.internalRegister();
    }

    private BinaryValueHandlerDescriptor<A, B, V> internalRegister() {
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
