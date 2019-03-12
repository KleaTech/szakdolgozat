package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.EventGroup;
import java.util.Properties;

public interface EventGroupService extends BaseService<EventGroup> {
    EventGroup add(String name, String template, Properties infos);
}
