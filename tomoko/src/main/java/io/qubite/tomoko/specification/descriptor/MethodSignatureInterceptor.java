package io.qubite.tomoko.specification.descriptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MethodSignatureInterceptor implements MethodInterceptor {

    private Method currentMethod;

    public Object intercept(Object o, Method method, Object[] os, MethodProxy mp) throws Throwable {
        currentMethod = method;
        return null;
    }

    public Method getCurrentMethod() {
        return currentMethod;
    }

}