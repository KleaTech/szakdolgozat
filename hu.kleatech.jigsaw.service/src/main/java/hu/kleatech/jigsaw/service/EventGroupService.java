package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.model.EventGroup;

import java.util.List;
import java.util.Properties;
import hu.kleatech.jigsaw.persistence.EventGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventGroupService implements hu.kleatech.jigsaw.service.interfaces.EventGroupService {

    @Autowired EventGroupRepository eventGroupRepository;

    @Override
    public EventGroup add(String name, String template, Properties infos) {
        return eventGroupRepository.save(new EventGroup(name, template, infos));
    }

    @Override
    public List<EventGroup> getAll() {
        return eventGroupRepository.findAll();
    }

    @Override
    public EventGroup add(EventGroup entity) {
        return eventGroupRepository.save(entity);
    }

    @Override
    public void delete(EventGroup entity) {
        eventGroupRepository.delete(entity);
    }

    @Override
    public EventGroup get(EventGroup entity) {
        return eventGroupRepository.getOne(entity.getId());
    }

    @Override
    public EventGroup get(Long id) {
        return eventGroupRepository.getOne(id);
    }

    @Override
    public EventGroup replace(EventGroup old, EventGroup nevv) {
        old.overwrite(nevv);
        return eventGroupRepository.save(old);
    }
}
