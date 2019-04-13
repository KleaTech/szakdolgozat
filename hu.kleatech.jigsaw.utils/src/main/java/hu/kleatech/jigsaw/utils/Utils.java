package hu.kleatech.jigsaw.utils;

import static hu.kleatech.jigsaw.utils.Constants.USER_DIR;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Utils {
    @FunctionalInterface
    public interface VoidExceptionFunction {
        void action() throws Exception;
    }

    @FunctionalInterface
    public interface GenericExceptionFunction<T> {
        T action() throws Exception;
    }

    public static Path relativePath(String... pieces) {
        Path path = USER_DIR;
        for (String piece : pieces) path = path.resolve(piece);
        return path;
    }
    public static void Try(VoidExceptionFunction func) {
        try {
            func.action();
        }
        catch (Exception e) {
            System.out.println("Try catched " + e.getMessage());
        }
    }
    public static <T> T TryOrNull(GenericExceptionFunction<T> func) {
        try {
            return func.action();
        }
        catch (Exception e) {
            System.out.println("Try catched " + e.getMessage());
            return null;
        }
    }
    public static <T> void DoNotOptimiseOut(T... obj) {
        for(T o : obj) try { obj.wait(0, 1); } catch (Exception e) {}
    }
}
