package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.model.Competition;
import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.model.Round;
import hu.kleatech.jigsaw.persistence.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Properties;

@Service
public class RoundService implements hu.kleatech.jigsaw.service.interfaces.RoundService {

    @Autowired RoundRepository roundRepository;

    @Override
    public Round add(Participant participant, Competition competition, Properties infos) {
        return roundRepository.save(new Round(participant, competition, infos));
    }

    @Override
    public List<Round> getAll() {
        return roundRepository.findAll();
    }

    @Override
    public Round add(Round entity) {
        return roundRepository.save(entity);
    }

    @Override
    public void delete(Round entity) {
        roundRepository.delete(entity);
    }

    @Override
    public Round get(Round entity) {
        return roundRepository.getOne(entity.getId());
    }

    @Override
    public Round get(Long id) {
        return roundRepository.getOne(id);
    }

    @Override
    public Round replace(Round old, Round nevv) {
        old.overwrite(nevv);
        roundRepository.flush();
        return roundRepository.save(old);
    }
}
