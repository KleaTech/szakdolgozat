package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.model.Sex;
import hu.kleatech.jigsaw.model.Team;

import java.time.LocalDate;
import java.util.Properties;

public interface ParticipantService extends BaseService<Participant> {
    Participant add(String name, LocalDate birthDate, Sex sex, Team team, String template, Properties infos);
}
