package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.handler.HandlerFactory;
import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.PatcherTreeSpecificationBuilder;
import io.qubite.tomoko.specification.descriptor.valueless.NullaryValuelessHandlerDescriptor;
import io.qubite.tomoko.specification.scanner.PathPattern;

public class NullaryValuelessHandlerSpec {

    private final PathPattern pathPattern;
    private final Runnable handler;
    private final PatcherTreeSpecificationBuilder builder;
    private final HandlerFactory handlerFactory;

    NullaryValuelessHandlerSpec(PathPattern pathPattern, Runnable handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        this.pathPattern = pathPattern;
        this.handler = handler;
        this.builder = builder;
        this.handlerFactory = handlerFactory;
    }

    public static NullaryValuelessHandlerSpec of(PathPattern pathPattern, Runnable handler, PatcherTreeSpecificationBuilder builder, HandlerFactory handlerFactory) {
        return new NullaryValuelessHandlerSpec(pathPattern, handler, builder, handlerFactory);
    }

    /**
     * Completes the path template definition and registers a handler.
     */
    public NullaryValuelessHandlerDescriptor register() {
        builder.handleRemove(PathTemplate.from(pathPattern), handlerFactory.handler(handler));
        return new NullaryValuelessHandlerDescriptor(pathPattern);
    }

}
