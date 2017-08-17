package io.qubite.tomoko.specification.descriptor;

public class MethodProxyDummy {

    public void parameterlessMethod() {

    }

    public String integerToStringFunction(Integer integer) {
        return Integer.toString(integer);
    }

    public String intPrimitiveToString(int integer) {
        return "";
    }

}
