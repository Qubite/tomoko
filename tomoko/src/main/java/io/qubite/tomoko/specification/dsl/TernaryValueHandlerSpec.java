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
import io.qubite.tomoko.specification.descriptor.value.TernaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.ConfigurationExtractor;
import io.qubite.tomoko.specification.scanner.ParameterDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.type.TypeExtractor;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;
import io.qubite.tomoko.util.Preconditions;
import io.qubite.tomoko.util.QuadConsumer;

/**
 * Handler configuration phase DSL. For more information check {@link io.qubite.tomoko.specification.dsl}.
 *
 * @param <A> handler first parameter type
 * @param <B> handler second parameter type
 * @param <C> handler third parameter type
 * @param <V> handler value type
 */
public class TernaryValueHandlerSpec<A, B, C, V> {

    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final CommandType commandType;
    private final PathPattern pathPattern;
    private final QuadConsumer<A, B, C, V> handler;

    private final ParameterConfiguration<A> firstParameterOverride;
    private final ParameterConfiguration<B> secondParameterOverride;
    private final ParameterConfiguration<C> thirdParameterOverride;
    private final ValueType<V> valueTypeOverride;


    TernaryValueHandlerSpec(CommandType commandType, PathPattern pathPattern, QuadConsumer<A, B, C, V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ParameterConfiguration<A> firstParameterOverride, ParameterConfiguration<B> secondParameterOverride, ParameterConfiguration<C> thirdParameterOverride, ValueType<V> valueTypeOverride) {
        this.commandType = commandType;
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.firstParameterOverride = firstParameterOverride;
        this.secondParameterOverride = secondParameterOverride;
        this.thirdParameterOverride = thirdParameterOverride;
        this.valueTypeOverride = valueTypeOverride;
    }

    public static <A, B, C, V> TernaryValueHandlerSpec<A, B, C, V> of(CommandType commandType, PathPattern pathPattern, QuadConsumer<A, B, C, V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new TernaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, null, null, null, null);
    }

    /**
     * Use {@link Types} class to override the extracted value type. Must be used if the type cannot be infered. This can happen when the handler is a mock/proxy or the type is generic.
     *
     * @param valueType
     * @return ongoing handler configuration
     */
    public TernaryValueHandlerSpec<A, B, C, V> value(ValueType<V> valueType) {
        return new TernaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, secondParameterOverride, thirdParameterOverride, valueType);
    }

    /**
     * Shorthand for value(Types.simple(valueRawClass)).
     *
     * @param valueRawClass
     * @return
     */
    public TernaryValueHandlerSpec<A, B, C, V> simpleValue(Class<V> valueRawClass) {
        return value(Types.simple(valueRawClass));
    }

    public TernaryValueHandlerSpec<A, B, C, V> firstArgument(String name, PathParameterConverter<A> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, parameter, secondParameterOverride, thirdParameterOverride, valueTypeOverride);
    }

    public TernaryValueHandlerSpec<A, B, C, V> firstArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValueHandlerSpec<A, B, C, V> firstArgument(String name, Class<A> argumentType) {
        return firstArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private TernaryValueHandlerSpec<A, B, C, V> inferFirstArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<A> parameterClass = (Class<A>) lambda.extractParameterClass(0);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return firstArgument(lambda.extractName(0), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValueHandlerSpec<A, B, C, V> secondArgument(String name, PathParameterConverter<B> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, parameter, thirdParameterOverride, valueTypeOverride);
    }

    public TernaryValueHandlerSpec<A, B, C, V> secondArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<B> parameterClass = (Class<B>) lambda.extractParameterClass(1);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValueHandlerSpec<A, B, C, V> secondArgument(String name, Class<B> argumentType) {
        return secondArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private TernaryValueHandlerSpec<A, B, C, V> inferSecondArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<B> parameterClass = (Class<B>) lambda.extractParameterClass(1);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return secondArgument(lambda.extractName(1), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValueHandlerSpec<A, B, C, V> thirdArgument(String name, PathParameterConverter<C> converter) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(converter);
        ParameterConfiguration parameter = ParameterConfiguration.of(name, converter);
        return new TernaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, firstParameterOverride, secondParameterOverride, parameter, valueTypeOverride);
    }

    public TernaryValueHandlerSpec<A, B, C, V> thirdArgument(String name) {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<C> parameterClass = (Class<C>) lambda.extractParameterClass(2);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return thirdArgument(name, ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
    }

    public TernaryValueHandlerSpec<A, B, C, V> thirdArgument(String name, Class<C> argumentType) {
        return thirdArgument(name, ConfigurationExtractor.instance().getDefaultConverter(argumentType));
    }

    private TernaryValueHandlerSpec<A, B, C, V> inferThirdArgument() {
        LambdaDescriptor<?> lambda = LambdaDescriptor.of(handler);
        Class<C> parameterClass = (Class<C>) lambda.extractParameterClass(2);
        Preconditions.checkNotUnknown(parameterClass, "Parameter type cannot be infered. Set converter directly.");
        return thirdArgument(lambda.extractName(2), ConfigurationExtractor.instance().getDefaultConverter(parameterClass));
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
    public TernaryValueHandlerDescriptor<A, B, C, V> register() {
        TernaryValueHandlerSpec<A, B, C, V> spec = this;
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

    private TernaryValueHandlerDescriptor<A, B, C, V> internalRegister() {
        ValueType<V> finalType = valueTypeOverride == null ? TypeExtractor.extractSimpleType(handler) : valueTypeOverride;
        PathParameter<A> firstParameter = PathParameterFactory.instance().toPathParameter(firstParameterOverride, pathPattern);
        PathParameter<B> secondParameter = PathParameterFactory.instance().toPathParameter(secondParameterOverride, pathPattern);
        PathParameter<C> thirdParameter = PathParameterFactory.instance().toPathParameter(thirdParameterOverride, pathPattern);
        ParameterDescriptor<A> firstParameterDescriptor = ParameterDescriptor.of(firstParameterOverride.getParameterName(), firstParameterOverride.getConverter());
        ParameterDescriptor<B> secondParameterDescriptor = ParameterDescriptor.of(secondParameterOverride.getParameterName(), secondParameterOverride.getConverter());
        ParameterDescriptor<C> thirdParameterDescriptor = ParameterDescriptor.of(thirdParameterOverride.getParameterName(), thirdParameterOverride.getConverter());
        if (commandType == CommandType.ADD) {
            builder.handleAdd(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, firstParameter, secondParameter, thirdParameter, handler));
        } else {
            builder.handleReplace(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, firstParameter, secondParameter, thirdParameter, handler));
        }
        return new TernaryValueHandlerDescriptor<>(commandType, pathPattern, firstParameterDescriptor, secondParameterDescriptor, thirdParameterDescriptor);
    }

}
