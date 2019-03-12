package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.Competition;
import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.model.Round;

import java.util.Properties;

public interface RoundService extends BaseService<Round> {
    Round add(Participant participant, Competition competition, Properties infos);
}
