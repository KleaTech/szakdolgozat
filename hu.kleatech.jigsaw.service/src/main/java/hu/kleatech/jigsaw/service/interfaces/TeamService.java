package hu.kleatech.jigsaw.service.interfaces;

import hu.kleatech.jigsaw.model.Team;
import java.util.Properties;

public interface TeamService extends BaseService<Team> {
    Team add(String name, String template, Properties infos);
}
