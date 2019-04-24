package hu.kleatech.jigsaw.utils;

import static hu.kleatech.jigsaw.utils.Constants.USER_DIR;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.file.Path;

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
        catch (UndeclaredThrowableException ex) {
            System.out.println("Try catched " + ex.getUndeclaredThrowable().getMessage());
            return null;
        }
        catch (Exception e) {
            System.out.println("Try catched " + e.getMessage());
            return null;
        }
    }
    public static <E extends Exception> void Try(Class<E> exClass, VoidExceptionFunction func) {
        try {
            func.action();
        }
        catch (Exception e) {
            if (!(e.getClass().isAssignableFrom(exClass))) throw new RuntimeException("Try exception, see inner", e);
            System.out.println("Try catched " + e.getMessage());
        }
    }
    public static <T, E extends Exception> T TryOrNull(Class<E> exClass, GenericExceptionFunction<T> func) {
        try {
            return func.action();
        }
        catch (Exception e) {
            if (!(e.getClass().isAssignableFrom(exClass))) throw new RuntimeException("Try exception, see inner", e);;
            System.out.println("Try catched " + e.getMessage());
            return null;
        }
    }
    public static <T> void DoNotOptimiseOut(T... obj) {
        for(T o : obj) try { obj.wait(0, 1); } catch (Exception e) {}
    }
}
