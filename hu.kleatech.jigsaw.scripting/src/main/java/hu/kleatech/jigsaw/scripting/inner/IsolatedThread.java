package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.MyScriptException;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

public class IsolatedThread extends Thread {
    private final Object pass = new Object();
    private final MyClassLoader loader = new MyClassLoader();
    private final MySecurityManager sm = new MySecurityManager(pass);
    private final RequestType requestType;
    public final PollingFuture<Function<List<Double>, List<Double>>> preresults = new PollingFuture<>();
    public final PollingFuture<Function<List<Double>, Double>> result = new PollingFuture<>();
    public final PollingFuture<DoubleSupplier> test = new PollingFuture<>();
    private final Object[] params;

    public IsolatedThread(RequestType requestType, Object... params) {
        super("IsolationThread");
        this.requestType = requestType;
        this.params = params;
    }

    @Override public void run() {
        sm.enable();
        runUntrustedCode();
        sm.disable(pass);
    }

    private void runUntrustedCode() {
        try {
            String className = "hu.kleatech.jigsaw.scripting.inner.EngineInner";
            Object scriptResult = loader.loadClass(className)
                    .getMethod(requestType.methodName, requestType.signature)
                    .invoke(null, params);
            switch (requestType) {
                case PRERESULTS:    preresults.set((Function<List<Double>, List<Double>>) scriptResult);
                                    result.set((s) -> { throw new IllegalStateException(); });
                                    test.set(() -> { throw new IllegalStateException(); });
                                    break;
                case RESULT:        result.set((Function<List<Double>, Double>) scriptResult);
                                    preresults.set((s) -> { throw new IllegalStateException(); });
                                    test.set(() -> { throw new IllegalStateException(); });
                                    break;
                case TEST:          test.set((DoubleSupplier) scriptResult);
                                    result.set((s) -> { throw new IllegalStateException(); });
                                    preresults.set((s) -> { throw new IllegalStateException(); });
                                    break;
                default:            throw new IllegalArgumentException(requestType.name() + "is not supported.");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new MyScriptException("Error when running external script: " + t);
        }
    }
}