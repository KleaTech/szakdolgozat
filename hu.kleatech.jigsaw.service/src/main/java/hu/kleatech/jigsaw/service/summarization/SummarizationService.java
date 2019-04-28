package hu.kleatech.jigsaw.service.summarization;

import hu.kleatech.jigsaw.model.EventGroup;
import java.io.FileNotFoundException;

public interface SummarizationService {
    SummarizationResults summarize(EventGroup eventGroup, Order order) throws FileNotFoundException;
    
    public static enum Order {
        ASCENDING, DESCENDING
    }
}
