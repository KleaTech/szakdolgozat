package hu.kleatech.jigsaw.scripting;

import hu.kleatech.jigsaw.api.MyScriptException;
import hu.kleatech.jigsaw.scripting.inner.IsolatedThread;
import hu.kleatech.jigsaw.scripting.inner.RequestType;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import javax.script.*;

public class SecureEngine implements hu.kleatech.jigsaw.api.EngineProvider.Engine {
    private final Path scriptPath;
    private final ScriptEngineManager engineManager = new ScriptEngineManager();
    
    SecureEngine(Path scriptPath) {
        this.scriptPath = scriptPath;
    }

    private ScriptEngine resolveEngine(String filename) {
        String[] str = filename.split("\\.");
        ScriptEngine engine = engineManager.getEngineByExtension(str[str.length-1]);
        return engine;
    }

    private FileReader resolveReader(String fileName) throws FileNotFoundException {
        return new FileReader(scriptPath.resolve(fileName).toFile());
    }

    @Override
    public Function<List<Double>, List<Double>> preresults(String filename) throws FileNotFoundException, MyScriptException, ClassCastException {
        try {
            IsolatedThread isolatedThread = new IsolatedThread(RequestType.PRERESULTS, resolveEngine(filename), resolveReader(filename));
            isolatedThread.start();
            return isolatedThread.preresults.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new MyScriptException("Timeout while running external script");
        } catch (InterruptedException | ExecutionException e) {
            throw new MyScriptException("Interruped while running external script: " + e.getMessage());
        } catch (ClassCastException e) {
            throw new MyScriptException(e.getMessage());
        }
    }

    @Override
    public Function<List<Double>, Double> result(String filename) throws FileNotFoundException, MyScriptException, ClassCastException {
        try {
            IsolatedThread isolatedThread = new IsolatedThread(RequestType.RESULT, resolveEngine(filename), resolveReader(filename));
            isolatedThread.start();
            return isolatedThread.result.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new MyScriptException("Timeout while running external script");
        } catch (InterruptedException | ExecutionException e) {
            throw new MyScriptException("Interruped while running external script: " + e.getMessage());
        } catch (ClassCastException e) {
            throw new MyScriptException(e.getMessage());
        }
    }
    
    public DoubleSupplier test() throws MyScriptException, ClassCastException {
        try {
            IsolatedThread isolatedThread = new IsolatedThread(RequestType.TEST, engineManager.getEngineByName("nashorn"));
            isolatedThread.start();
            return isolatedThread.test.get(1, TimeUnit.HOURS);
        } catch (TimeoutException e) {
            throw new MyScriptException("Timeout while running external script");
        } catch (InterruptedException | ExecutionException e) {
            throw new MyScriptException("Interruped while running external script: " + e.getMessage());
        } catch (ClassCastException e) {
            throw new MyScriptException(e.getMessage());
        }
    }
}