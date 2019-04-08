package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.Event;
import hu.kleatech.jigsaw.model.EventGroup;

import java.util.Properties;

public interface EventService extends BaseService<Event> {
    Event add(EventGroup eventGroup, String name, String template, Properties infos);
}
