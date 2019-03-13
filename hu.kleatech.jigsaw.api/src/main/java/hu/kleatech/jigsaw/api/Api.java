package hu.kleatech.jigsaw.api;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Api {

    public static final class Statistics {
        private Statistics() {}
        public static final Statistics STATISTICS = new Statistics();
        public final Function<double[], Double> MEAN = m -> {
            double sum = 0;
            for (int i = 0; i < m.length; i++) {
                sum += m[i];
            }
            return sum / m.length;
        };
        public final Function<double[], Double> MEDIAN = m -> {
            int middle = m.length/2;
            if (m.length%2 == 1) {
                return m[middle];
            } else {
                return (m[middle-1] + m[middle]) / 2.0;
            }
        };
    }
}
