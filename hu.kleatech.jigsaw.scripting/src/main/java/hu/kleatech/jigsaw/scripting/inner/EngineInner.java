package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.MyScriptException;
import java.io.FileReader;
import java.util.List;
import java.util.function.Function;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

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
}