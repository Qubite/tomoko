/**
 * <p>
 * Domain specific language for configuring paths and registering handlers for patch operations.
 * </p>
 * <p>
 * The starting point is the {@link io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL} class.
 * From there you can either start a new path, register a set of handlers through class scanning or end the configuration phase and create a new {@link io.qubite.tomoko.patcher.Patcher Patcher}.
 * </p>
 * <p>
 * To register a handler explicitly first you have to define a path using {@link io.qubite.tomoko.specification.dsl.PathDSL} that is available through the {@link io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL#path(java.lang.String)} method.
 * When a path is ready then you can proceed to the handler configuration phase by providing a handler in one of handle* methods.
 * </p>
 * <p>
 * Each handler configuration phase class will try to infer the value type of a handler, its parameter names and type converters.
 * If it is not possible or it you need a special configuration then the handler can be described explicitly through *Argument() and value() methods.
 * </p>
 * <p>
 * Example configuration explicitly registering a void updateTitle(String newTitle) method on path "/tickets/{ticketId}/title".
 * This example uses DirectTomoko but it will work the same way for JacksonTomoko and GsonTomoko.
 * </p>
 * <pre>
 * {@code
 *
 * HandlerConfigurationDSL dsl = DirectTomoko.instance().specificationDsl();
 * dsl.path("/tickets/{ticketId:[0-9]+}/title").handleAdd(this::updateTitle)
 *          .firstArgument("ticketId", String.class).simpleValue(String.class).register();
 * Patcher patcher = dsl.toPatcher();
 * }
 * </pre>
 * <p>
 * All classes except {@link io.qubite.tomoko.specification.dsl.HandlerConfigurationDSL} are immutable.
 * </p>
 */
package io.qubite.tomoko.specification.dsl;