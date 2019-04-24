package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.Competition;
import hu.kleatech.jigsaw.utils.Constants;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

final class ControllerUtils {
    
    private ControllerUtils() {}
    
    static Path scriptPath(Competition comp) {
        return relativePath(Constants.MODULES_DIR_NAME, comp.getEvent().getEventGroup().getName());
    }
    
    static String scriptName(Competition comp, ResultType type) throws FileNotFoundException {
        if (type == ResultType.PRERESULT) {
            Optional<String> name = Arrays.stream(scriptPath(comp).toFile().listFiles())
                    .map(f -> f.getName())
                    .filter(f -> f.startsWith(comp.getTemplate()))
                    .filter(f -> f.replaceFirst("[.][^.]+$", "").endsWith("_pre"))
                    .findFirst();
            if (!name.isPresent()) throw new FileNotFoundException(comp.getTemplate());
            return name.get();
        } else if (type == ResultType.RESULT) {
            Optional<String> name = Arrays.stream(scriptPath(comp).toFile().listFiles())
                    .map(f -> f.getName())
                    .filter(f -> f.startsWith(comp.getTemplate()))
                    .filter(f -> !f.endsWith("html"))
                    .filter(f -> !f.replaceFirst("[.][^.]+$", "").endsWith("_pre"))
                    .findFirst();
            if (!name.isPresent()) throw new FileNotFoundException(comp.getTemplate());
            return name.get();
        }
        throw new IllegalArgumentException(type.name() + "is not supported!");
    }
    
    static enum ResultType { PRERESULT, RESULT }
}
