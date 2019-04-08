package hu.kleatech.jigsaw.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class StringUtils {
    public static String nullsafe(Supplier<Object> printableObjectSupplier) {
        try {
            return Objects.toString(printableObjectSupplier.get());
        }
        catch (NullPointerException e) {
            return "null";
        }
    }
    public static String concat(String delimiter, Object... printableObjects) {
        return Arrays.stream(printableObjects).map(o -> Objects.toString(o)).collect(Collectors.joining(delimiter));
    }
}
