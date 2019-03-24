package hu.kleatech.jigsaw.scripting;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class Engine {

    private final Path scriptsDirectory;
    private final ScriptEngineManager engineManager = new ScriptEngineManager();

    public Engine(Path scriptsDirectory) {
        this.scriptsDirectory = scriptsDirectory;
    }

    private ScriptEngine resolveEngine(String filename) {
        String[] str = filename.split("\\.");
        ScriptEngine engine = engineManager.getEngineByExtension(str[str.length-1]);
        return engine;
    }

    private FileReader resolveReader(String fileName) throws FileNotFoundException {
        return new FileReader(scriptsDirectory.resolve(fileName).toFile());
    }

    public void test(String filename) throws FileNotFoundException, MyScriptException {
        try {
            ScriptEngine engine = resolveEngine(filename);
            engine.eval(resolveReader(filename));
        }
        catch (ScriptException e) {
            throw new MyScriptException(e);
        }
    }

    public Function<List<Double>, List<Double>> preresults(String filename) throws FileNotFoundException, MyScriptException, ClassCastException {
        try {
            ScriptEngine engine = resolveEngine(filename);
            engine.eval(resolveReader(filename));
            Invocable inv = (Invocable) engine;
            return inv.getInterface(Function.class);
        }
        catch (ScriptException e) {
            throw new MyScriptException(e);
        }
    }
    
    public Function<List<Double>, Double> result(String filename) throws FileNotFoundException, MyScriptException, ClassCastException {
        try {
            ScriptEngine engine = resolveEngine(filename);
            engine.eval(resolveReader(filename));
            Invocable inv = (Invocable) engine;
            return inv.getInterface(Function.class);
        }
        catch (ScriptException e) {
            throw new MyScriptException(e);
        }
    }

    /*public void testPutAndGet() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.put("statistics", Api.Statistics.STATISTICS);
        try {
            engine.eval("var array = [1,2,3,4,5];" +
                    "function getAsDouble() { return statistics.MEDIAN.apply(Java.to(array, 'double[]')); }");
            Invocable inv = (Invocable) engine;
            DoubleSupplier r = inv.getInterface(DoubleSupplier.class);
            System.out.println(Double.isFinite(r.getAsDouble())?"Test successful":"Test failed");
        }
        catch (ScriptException e) {
            System.out.println("Test failed");
        }
    }*/
}
