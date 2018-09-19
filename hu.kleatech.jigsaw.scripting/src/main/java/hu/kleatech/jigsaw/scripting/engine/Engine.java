package hu.kleatech.jigsaw.scripting.engine;

import hu.kleatech.jigsaw.api.Api;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.function.DoubleSupplier;

public class Engine {

    private final Path scriptsDirectory;
    private final ScriptEngineManager engineManager = new ScriptEngineManager();

    public Engine(Path scriptsDirectory) {
        this.scriptsDirectory = scriptsDirectory;
    }

    public void loadVoid(String filename) throws FileNotFoundException, ScriptException {
        engineManager.getEngineByExtension(filename.split("\\.")[1])
                .eval(new FileReader(scriptsDirectory.resolve(filename).toFile()));
    };

    public void test() {
        try {
            loadVoid("testVoid.js");
        }
        catch (ScriptException e) {
            System.out.println("Test failed");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }
    }

    public void testPut() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.put("statistics", Api.Statistics.STATISTICS);
        try {
            engine.eval("var array = [1,2,3,4,5];" +
                    "if (statistics.MEDIAN.apply(Java.to(array, 'double[]'))==3) {" +
                    "print('Test successful')}" +
                    "else {" +
                    "print('Test failed')};");
        } catch (ScriptException e) {
            System.out.println("Test failed");
        }
    }

    public void testPutAndGet() {
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
    }
}
