package hu.kleatech.jigsaw.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final Path USER_DIR = Paths.get(System.getProperty("user.dir")).getParent();
}
