package io.qubite.tomoko.handler.invocation;

import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.handler.HandlerExecutionException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.WrongMethodTypeException;

public class MethodHandleInvocation implements ClientCodeInvocation {

    private final MethodHandle methodHandle;

    MethodHandleInvocation(MethodHandle methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public void invoke(Object[] parameters) {
        try {
            methodHandle.invokeExact(parameters);
        } catch (WrongMethodTypeException e) {
            throw new TomokoException("Problem with Tomoko itself.", e);
        } catch (Throwable throwable) {
            if (throwable instanceof Error) {
                throw (Error) throwable;
            } else if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            } else {
                throw new HandlerExecutionException((Exception) throwable);
            }
        }
    }

}
