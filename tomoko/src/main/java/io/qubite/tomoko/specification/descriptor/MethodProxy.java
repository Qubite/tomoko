package io.qubite.tomoko.specification.descriptor;

import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.QuinConsumer;
import io.qubite.tomoko.util.TriConsumer;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.objenesis.ObjenesisHelper;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public Method getMethod(Function<T, ?> function) {
        function.apply(proxied);
        return interceptor.getCurrentMethod();
    }

    public Method getMethod(Consumer<T> handler) {
        handler.accept(proxied);
        return interceptor.getCurrentMethod();
    }

    public Method getMethod(BiConsumer<T, ?> consumer) {
        consumer.accept(proxied, null);
        return interceptor.getCurrentMethod();
    }

    public Method getMethod(TriConsumer<T, ?, ?> consumer) {
        consumer.accept(proxied, null, null);
        return interceptor.getCurrentMethod();
    }

    public Method getMethod(QuadConsumer<T, ?, ?, ?> consumer) {
        consumer.accept(proxied, null, null, null);
        return interceptor.getCurrentMethod();
    }

    public Method getMethod(QuinConsumer<T, ?, ?, ?, ?> consumer) {
        consumer.accept(proxied, null, null, null, null);
        return interceptor.getCurrentMethod();
    }

}
