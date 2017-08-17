package io.qubite.tomoko.handler.invocation;

public class RunnableInvocation implements ClientCodeInvocation {

    private final Runnable runnable;

    RunnableInvocation(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void invoke(Object[] parameters) {
        runnable.run();
    }

}
