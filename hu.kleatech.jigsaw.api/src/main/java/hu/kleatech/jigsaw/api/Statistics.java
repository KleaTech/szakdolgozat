package hu.kleatech.jigsaw.api;

import java.util.function.Function;

public interface Statistics {
    Function<double[], Double> getMean();
    Function<double[], Double> getMedian();
}
