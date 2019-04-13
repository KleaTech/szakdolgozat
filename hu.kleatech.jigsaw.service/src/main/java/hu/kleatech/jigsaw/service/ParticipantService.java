package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.model.Sex;
import hu.kleatech.jigsaw.model.Team;
import hu.kleatech.jigsaw.persistence.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Service
public class ParticipantService implements hu.kleatech.jigsaw.service.interfaces.ParticipantService {

    @Autowired ParticipantRepository participantRepository;

    @Override
    public Participant add(String name, LocalDate birthDate, Sex sex, Team team, Properties infos) {
        return participantRepository.save(new Participant(name, birthDate, sex, team, infos));
    }

    @Override
    public List<Participant> getAll() {
        return participantRepository.findAll();
    }

    @Override
    public Participant add(Participant entity) {
        return participantRepository.save(entity);
    }

    @Override
    public void delete(Participant entity) {
        participantRepository.delete(entity);
    }

    @Override
    public Participant get(Participant entity) {
        return participantRepository.getOne(entity.getId());
    }

    @Override
    public Participant get(Long id) {
        return participantRepository.getOne(id);
    }

    @Override
    public Participant replace(Participant old, Participant nevv) {
        old.overwrite(nevv);
        return participantRepository.save(old);
    }
}
