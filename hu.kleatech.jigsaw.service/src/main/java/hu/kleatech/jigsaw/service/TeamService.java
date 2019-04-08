package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.model.EventGroup;
import hu.kleatech.jigsaw.model.Team;
import hu.kleatech.jigsaw.persistence.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Properties;

@Service
public class TeamService implements hu.kleatech.jigsaw.service.interfaces.TeamService {

    @Autowired TeamRepository teamRepository;
    @Autowired ParticipantService participantService;

    @Override
    public Team add(String name, String template, EventGroup associatedEventGroup, Properties infos) {
        return teamRepository.save(new Team(name, template, associatedEventGroup, infos));
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team add(Team entity) {
        return teamRepository.save(entity);
    }

    @Override
    public void delete(Team entity) {
        entity.getParticipants().forEach(p -> participantService.delete(p));
        teamRepository.delete(entity);
    }

    @Override
    public Team get(Team entity) {
        return teamRepository.getOne(entity.getId());
    }

    @Override
    public Team get(Long id) {
        return teamRepository.getOne(id);
    }

    @Override
    public Team replace(Team old, Team nevv) {
        old.overwrite(nevv);
        return teamRepository.save(old);
    }
}
