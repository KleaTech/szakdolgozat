package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.MyScriptException;

public class IsolatedThread<T> extends Thread {
    private final Object pass = new Object();
    private final MyClassLoader loader = new MyClassLoader();
    private final MySecurityManager sm = new MySecurityManager(pass);
    private final RequestType requestType;
    public final PollingFuture<T> result = new PollingFuture<>();
    private final Object[] params;

    public IsolatedThread(RequestType requestType, Object... params) {
        super("IsolationThread");
        this.requestType = requestType;
        this.params = params;
    }

    @Override public void run() {
        sm.enable();
        try {
            runUntrustedCode();
        } finally {
            sm.disable(pass);
        }
    }

    @Override
    public void interrupt() {
        sm.disable(pass);
        stop();
    }
    
    private void runUntrustedCode() {
        try {
            String className = "hu.kleatech.jigsaw.scripting.inner.EngineInner";
            Object scriptResult = loader.loadClass(className)
                    .getMethod(requestType.methodName, requestType.signature)
                    .invoke(null, params);
            result.set((T) scriptResult);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new MyScriptException("Error when running external script: " + t.getMessage());
        }
    }
}