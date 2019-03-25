package hu.kleatech.jigsaw.api;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public interface EngineProvider {
    Engine getEngine(Path scriptPath);
    static interface Engine {
        Function<List<Double>, List<Double>> preresults(String filename) throws FileNotFoundException, MyScriptException, ClassCastException;
        Function<List<Double>, Double> result(String filename) throws FileNotFoundException, MyScriptException, ClassCastException;
    }
}