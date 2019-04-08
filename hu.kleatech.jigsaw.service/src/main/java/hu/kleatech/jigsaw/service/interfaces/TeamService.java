package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.EventGroup;
import hu.kleatech.jigsaw.model.Team;
import java.util.Properties;

public interface TeamService extends BaseService<Team> {
    Team add(String name, String template, EventGroup associatedEventGroup, Properties infos);
}
