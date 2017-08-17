package io.qubite.tomoko.specification.descriptor;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class MethodProxyTest {

    @Test
    public void extractGetter() throws Exception {
        MethodProxy<MethodProxyDummy> proxy = MethodProxy.forClass(MethodProxyDummy.class);
        Method method = proxy.getMethod(MethodProxyDummy::parameterlessMethod);
        assertEquals("parameterlessMethod", method.getName());
    }

    @Test
    public void extractFunction() throws Exception {
        MethodProxy<MethodProxyDummy> proxy = MethodProxy.forClass(MethodProxyDummy.class);
        Method method = proxy.getMethod(MethodProxyDummy::integerToStringFunction);
        assertEquals("integerToStringFunction", method.getName());
    }

    @Test
    public void extractPrimitiveFunction() throws Exception {
        MethodProxy<MethodProxyDummy> proxy = MethodProxy.forClass(MethodProxyDummy.class);
        Method method = proxy.getMethod(MethodProxyDummy::intPrimitiveToString);
        assertEquals("intPrimitiveToString", method.getName());
    }

}
