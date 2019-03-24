package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.Competition;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.nio.file.Path;

final class ControllerUtils {
    
    private ControllerUtils() {}
    
    static Path scriptPath(Competition comp) {
        return relativePath("modules", comp.getEvent().getEventGroup().getName());
    }
    
    static String scriptName(Competition comp, ResultType type) {
        switch (type)
        {
            case PRERESULT: return comp.getTemplate().replaceFirst("[.][^.]+$", "") + "_pre.js";
            case RESULT: return comp.getTemplate().replaceFirst("[.][^.]+$", "") + ".js";
        }
        throw new IllegalArgumentException(type.name() + "is not supported!");
    }
    
    static enum ResultType { PRERESULT, RESULT }
}
