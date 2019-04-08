package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.model.Competition;
import hu.kleatech.jigsaw.model.Event;
import hu.kleatech.jigsaw.persistence.CompetitionRepository;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService implements hu.kleatech.jigsaw.service.interfaces.CompetitionService {

    @Autowired CompetitionRepository competitionRepository;

    @Override
    public Competition add(Event event, String name, String template, Properties infos) {
        return competitionRepository.save(new Competition(event, name, template, infos));
    }

    @Override
    public List<Competition> getAll() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition add(Competition entity) {
        return competitionRepository.save(entity);
    }

    @Override
    public void delete(Competition entity) {
        competitionRepository.delete(entity);
    }

    @Override
    public Competition get(Competition entity) {
        return competitionRepository.getOne(entity.getId());
    }

    @Override
    public Competition get(Long id) {
        return competitionRepository.getOne(id);
    }

    @Override
    public Competition replace(Competition old, Competition nevv) {
        old.overwrite(nevv);
        return competitionRepository.save(old);
    }

    @Override
    public void refresh(Competition entity) {
        competitionRepository.refresh(entity);
    }
}
