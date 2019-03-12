package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.model.Event;
import hu.kleatech.jigsaw.model.EventGroup;
import hu.kleatech.jigsaw.persistence.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Properties;

@Service
public class EventService implements hu.kleatech.jigsaw.service.interfaces.EventService {

    @Autowired EventRepository eventRepository;

    @Override
    public Event add(EventGroup eventGroup, String name, String template, Properties infos) {
        return eventRepository.save(new Event(eventGroup, name, template, infos));
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event add(Event entity) {
        return eventRepository.save(entity);
    }

    @Override
    public void delete(Event entity) {
        eventRepository.delete(entity);
    }

    @Override
    public Event get(Event entity) {
        return eventRepository.getOne(entity.getId());
    }

    @Override
    public Event get(Long id) {
        return eventRepository.getOne(id);
    }

    @Override
    public Event replace(Event old, Event nevv) {
        old.overwrite(nevv);
        return eventRepository.save(old);
    }
}
