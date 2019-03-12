package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.Competition;
import hu.kleatech.jigsaw.model.Event;

import java.util.Properties;

public interface CompetitionService extends BaseService<Competition> {
    Competition add(Event event, String name, String template, Properties infos);
    void refresh(Competition entity);
}
