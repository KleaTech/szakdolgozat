package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.Dispatcher;
import hu.kleatech.jigsaw.api.MyScriptException;
import java.io.FileReader;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import javax.script.*;

//Public because of custom classloader
public class EngineInner {
    public static Function<List<Double>, List<Double>> preresults(ScriptEngine engine, FileReader file) throws MyScriptException, ClassCastException {
        try {
            engine.eval(file);
            Invocable inv = (Invocable) engine;
            return inv.getInterface(Function.class);
        }
        catch (ScriptException e) {
            throw new MyScriptException(e.getMessage(), e.getFileName(), e.getLineNumber(), e.getColumnNumber());
        }
    }

    public static Function<List<Double>, Double> result(ScriptEngine engine, FileReader file) throws MyScriptException, ClassCastException {
        try {
            engine.eval(file);
            Invocable inv = (Invocable) engine;
            return inv.getInterface(Function.class);
        }
        catch (ScriptException e) {
            throw new MyScriptException(e.getMessage(), e.getFileName(), e.getLineNumber(), e.getColumnNumber());
        }
    }
    
    public static DoubleSupplier testPutAndGet(ScriptEngine engine) throws MyScriptException, ClassCastException {
        engine.put("statistics", Dispatcher.getStatistics());
        try {
            engine.eval("var array = [1,2,3,4,5];" +
                    "function getAsDouble() { return statistics.getMedian().apply(Java.to(array, 'double[]')); }");
            Invocable inv = (Invocable) engine;
            DoubleSupplier r = inv.getInterface(DoubleSupplier.class);
            System.out.println(Double.isFinite(r.getAsDouble())?"Test successful":"Test failed");
            return r;
        }
        catch (ScriptException e) {
            System.out.println("Test failed");
        }
        throw new IllegalStateException("Should not be reachable");
    }
}