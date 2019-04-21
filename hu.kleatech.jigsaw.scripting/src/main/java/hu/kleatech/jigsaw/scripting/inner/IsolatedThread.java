package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.MyScriptException;
import java.io.FileReader;
import java.util.List;
import java.util.function.Function;
import javax.script.ScriptEngine;

public class IsolatedThread extends Thread {
    private final Object pass = new Object();
    private final MyClassLoader loader = new MyClassLoader();
    private final MySecurityManager sm = new MySecurityManager(pass);
    private final RequestType requestType;
    private final FileReader file;
    private final ScriptEngine engine;
    public final PollingFuture<Function<List<Double>, List<Double>>> preresults = new PollingFuture<>();
    public final PollingFuture<Function<List<Double>, Double>> result = new PollingFuture<>();

    public IsolatedThread(RequestType requestType, FileReader file, ScriptEngine engine) {
        super("IsolationThread");
        this.requestType = requestType;
        this.file = file;
        this.engine = engine;
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
                    .getMethod(requestType.methodName, ScriptEngine.class, FileReader.class)
                    .invoke(null, engine, file);
            switch (requestType) {
                case PRERESULTS:    preresults.set((Function<List<Double>, List<Double>>) scriptResult);
                                    result.set((s) -> { throw new IllegalStateException(); });
                                    break;
                case RESULT:        result.set((Function<List<Double>, Double>) scriptResult);
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