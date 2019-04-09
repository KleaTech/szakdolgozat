package hu.kleatech.jigsaw.utils;

import java.nio.file.*;
import java.util.*;

public class Constants {
    public static final String MODULES_DIR_NAME = "modules";
    public static final Path USER_DIR;
    public static final String LOADED_MODULES_DIR_NAME = "loadedModules";
    
    static {
        Path path = Paths.get(System.getProperty("user.dir"));
        final List<Path> checkedPaths = new LinkedList<>();
        do {
                checkedPaths.add(path);
                if (Files.exists(path.resolve(MODULES_DIR_NAME))) break;
                path = path.getParent();
        } while (path != null);
        if (path == null) throw new IllegalStateException("Cannot find root directory! Checked dirs: " + checkedPaths.toString());
        USER_DIR = path;
    }
}
