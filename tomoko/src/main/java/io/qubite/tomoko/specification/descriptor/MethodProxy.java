package io.qubite.tomoko.specification.descriptor;

import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.QuinConsumer;
import io.qubite.tomoko.util.TriConsumer;
import net.jodah.typetools.TypeResolver;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.objenesis.ObjenesisHelper;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Proxy to determine what method has been referenced in the provided argument. Supports methods with primitive parameters.<br/><br/>
 * Works for every class thanks to CGlib and Objenesis.
 *
 * @param <T> proxied type
 */
public class MethodProxy<T> {

    private final MethodSignatureInterceptor interceptor;
    private final T proxied;

    MethodProxy(MethodSignatureInterceptor interceptor, T proxied) {
        this.interceptor = interceptor;
        this.proxied = proxied;
    }

    public static <T> MethodProxy<T> forClass(Class<T> clazz) {
        MethodSignatureInterceptor methodSignatureInterceptor = new MethodSignatureInterceptor();
        final Enhancer enhancer = new Enhancer();
        enhancer.setUseCache(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallbackType(methodSignatureInterceptor.getClass());
        final Class<?> proxyClass = enhancer.createClass();
        Enhancer.registerCallbacks(proxyClass, new Callback[]{methodSignatureInterceptor});
        T proxied = (T) ObjenesisHelper.newInstance(proxyClass);
        return new MethodProxy<>(methodSignatureInterceptor, proxied);
    }

    public <V> Method getMethod(Function<T, V> function) {
        function.apply(proxied);
        return interceptor.getCurrentMethod();
    }

    public <A, V> Method getMethod(BiFunction<T, A, V> function) {
        Class<?>[] parameterClasses = TypeResolver.resolveRawArguments(BiFunction.class, function.getClass());
        function.apply(proxied, (A) getDefaultValue(parameterClasses[1]));
        return interceptor.getCurrentMethod();
    }

    public Method getMethod(Consumer<T> handler) {
        handler.accept(proxied);
        return interceptor.getCurrentMethod();
    }

    public <A> Method getMethod(BiConsumer<T, A> consumer) {
        Class<?>[] parameterClasses = TypeResolver.resolveRawArguments(BiConsumer.class, consumer.getClass());
        consumer.accept(proxied, (A) getDefaultValue(parameterClasses[1]));
        return interceptor.getCurrentMethod();
    }

    public <A, B> Method getMethod(TriConsumer<T, A, B> consumer) {
        Class<?>[] parameterClasses = TypeResolver.resolveRawArguments(TriConsumer.class, consumer.getClass());
        consumer.accept(proxied, (A) getDefaultValue(parameterClasses[1]), (B) getDefaultValue(parameterClasses[2]));
        return interceptor.getCurrentMethod();
    }

    public <A, B, C> Method getMethod(QuadConsumer<T, A, B, C> consumer) {
        Class<?>[] parameterClasses = TypeResolver.resolveRawArguments(QuadConsumer.class, consumer.getClass());
        consumer.accept(proxied, (A) getDefaultValue(parameterClasses[1]), (B) getDefaultValue(parameterClasses[2]), (C) getDefaultValue(parameterClasses[3]));
        return interceptor.getCurrentMethod();
    }

    public <A, B, C, D> Method getMethod(QuinConsumer<T, A, B, C, D> consumer) {
        Class<?>[] parameterClasses = TypeResolver.resolveRawArguments(QuinConsumer.class, consumer.getClass());
        consumer.accept(proxied, (A) getDefaultValue(parameterClasses[1]), (B) getDefaultValue(parameterClasses[2]), (C) getDefaultValue(parameterClasses[3]), (D) getDefaultValue(parameterClasses[4]));
        return interceptor.getCurrentMethod();
    }

    private Object getDefaultValue(Class<?> clazz) {
        Object result;
        if (clazz.equals(Integer.class) || clazz.equals(Short.class)) {
            result = 0;
        } else if (clazz.equals(Long.class)) {
            result = 0l;
        } else if (clazz.equals(Boolean.class)) {
            result = false;
        } else if (clazz.equals(Character.class)) {
            result = 'a';
        } else if (clazz.equals(Double.class)) {
            result = 0.0;
        } else if (clazz.equals(Float.class)) {
            result = 0f;
        } else {
            result = null;
        }
        return result;
    }

}
