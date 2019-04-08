package hu.kleatech.jigsaw.service;

import java.util.function.Function;
import hu.kleatech.jigsaw.api.Dispatcher;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Statistics implements hu.kleatech.jigsaw.api.Statistics {

    @EventListener(ApplicationStartedEvent.class)
    public static final void load() {
        Dispatcher.setStatistics(new Statistics());
    }
    
    @Override
    public Function<double[], Double> getMean() {
        return (m) -> {
            double sum = 0;
            for (int i = 0; i < m.length; i++) {
                sum += m[i];
            }
            return sum / m.length;
        };
    }

    @Override
    public Function<double[], Double> getMedian() {
        return (m) -> {
            int middle = m.length/2;
            if (m.length%2 == 1) {
                return m[middle];
            } else {
                return (m[middle-1] + m[middle]) / 2.0;
            }
        };
    }
    
}
