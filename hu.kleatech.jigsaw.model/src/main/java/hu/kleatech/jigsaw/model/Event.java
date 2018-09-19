package hu.kleatech.jigsaw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Entity
public class Event implements Serializable {
    @ManyToOne
    @JoinColumn(name = "events")
    private EventGroup eventGroup;

    @Column
    private String name;

    @Transient
    private List<Team> teams;

    @OneToMany(mappedBy ="event" )
    private List<Competition> competitions;

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected Event(){} //For serialization and prototype creation only
    public Event(EventGroup eventGroup, String name, List<Team> teams, List<Competition> competitions, Properties infos) {
        this.eventGroup = eventGroup;
        this.name = name;
        this.teams = teams;
        this.competitions = competitions;
        this.infos = infos;
    }

    public EventGroup getEventGroup() { return eventGroup; }
    public String getName() { return name; }
    public List<Team> getTeams() { return teams; }
    public List<Competition> getCompetitions() { return competitions; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return "Event{" + "eventGroup=" + eventGroup.getName() + ", name='" + name + '\'' + ", teams=" + teams.size() + ", competitions=" + competitions.size() + ", infos=" + infos.size() + '}'; }

    public void overwrite(Event newEvent) {
        this.competitions = newEvent.competitions;
        this.eventGroup = newEvent.eventGroup;
        this.infos = newEvent.infos;
        this.name = newEvent.name;
        this.teams = newEvent.teams;
    }
}
