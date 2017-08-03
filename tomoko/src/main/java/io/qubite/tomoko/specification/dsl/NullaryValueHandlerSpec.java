package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.value.NullaryValueHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;
import io.qubite.tomoko.type.TypeExtractor;
import io.qubite.tomoko.type.ValueType;

import java.util.function.Consumer;

public class NullaryValueHandlerSpec<V> {

    private final CommandType commandType;
    private final PathPattern pathPattern;
    private final Consumer<V> handler;
    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    private final ValueType<V> valueTypeOverride;

    NullaryValueHandlerSpec(CommandType commandType, PathPattern pathPattern, Consumer<V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory, ValueType<V> valueTypeOverride) {
        this.commandType = commandType;
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
        this.valueTypeOverride = valueTypeOverride;
    }

    public static <V> NullaryValueHandlerSpec<V> of(CommandType commandType, PathPattern pathPattern, Consumer<V> handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new NullaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, null);
    }

    /**
     * Use Types class to override the extracted value type.
     *
     * @param valueType
     * @return
     */
    public NullaryValueHandlerSpec<V> value(ValueType<V> valueType) {
        return new NullaryValueHandlerSpec<>(commandType, pathPattern, handler, builder, handlerFactory, valueType);
    }

    /**
     * Completes the path template definition and registers a handler.<br/><br/>
     * Value type is inferred from the handler's signature. Sometimes it is impossible e.g. when the handler is a mock or the type is generic.
     * In that case the type should be specified through the value() method.
     */
    public NullaryValueHandlerDescriptor<V> register() {
        ValueType<V> finalType = valueTypeOverride == null ? TypeExtractor.extractSimpleType(handler) : valueTypeOverride;
        if (commandType == CommandType.ADD) {
            builder.handleAdd(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, handler));
        } else {
            builder.handleReplace(PathTemplate.from(pathPattern), handlerFactory.handler(finalType, handler));
        }
        return new NullaryValueHandlerDescriptor<>(commandType, pathPattern);
    }
}
