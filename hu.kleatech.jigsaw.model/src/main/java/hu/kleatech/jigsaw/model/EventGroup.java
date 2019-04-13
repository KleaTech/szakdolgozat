package hu.kleatech.jigsaw.model;

import static hu.kleatech.jigsaw.utils.StringUtils.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class EventGroup implements Serializable{
    @OneToMany(mappedBy = "eventGroup", cascade = CascadeType.ALL)
    private List<Event> events;
    
    @OneToMany(mappedBy = "associatedEventGroup")
    private List<Team> associatedTeams;

    @Column
    private String name;

    @Column
    private String template;

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected EventGroup(){} //For serialization and prototype creation only
    public EventGroup(String name, String template, Properties infos) {
        this.name = name;
        this.infos = infos;
        this.template = template;
    }

    public List<Event> getEvents() { return events; }
    public String getName() { return name; }
    public String getTemplate() { return template; }
    public Long getId() { return id; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public List<Team> getAssociatedTeams() { return associatedTeams; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventGroup)) return false;
        EventGroup that = (EventGroup) o;
        return Objects.equals(getId(), that.getId());
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return concat("", "EventGroup{", "events=", nullsafe(()->events.size()), ", name='", name, '\'', ", infos=", getInfos(), '}');
    }

    public void overwrite(EventGroup newEventGroup) {
        this.infos = newEventGroup.infos;
        this.events = newEventGroup.events;
        this.name = newEventGroup.name;
        this.associatedTeams = newEventGroup.associatedTeams;
    }

}
