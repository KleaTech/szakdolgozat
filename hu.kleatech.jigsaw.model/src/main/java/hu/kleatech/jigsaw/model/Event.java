package hu.kleatech.jigsaw.model;

import static hu.kleatech.jigsaw.util.StringUtils.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Entity
public class Event implements Serializable {
    @ManyToOne
    @JoinColumn(name = "events")
    private EventGroup eventGroup;

    @Column
    private String name;

    @Transient
    private List<Team> teams;

    @OneToMany(mappedBy ="event", cascade = CascadeType.ALL)
    private List<Competition> competitions;

    @Column
    @Lob
    private Properties infos;

    @Column
    private String template;

    @Id
    @GeneratedValue
    private Long id;

    protected Event(){} //For serialization and prototype creation only
    public Event(EventGroup eventGroup, String name, String template, Properties infos) {
        this.eventGroup = eventGroup;
        this.name = name;
        this.infos = infos;
        this.template = template;
    }

    @PostLoad
    private void onLoad() {
        this.teams = getCompetitions().stream().map(c -> c.getTeams()).flatMap(t -> t.stream()).collect(Collectors.toList());
    }

    public EventGroup getEventGroup() { return eventGroup; }
    public String getName() { return name; }
    public List<Team> getTeams() { return teams; }
    public List<Competition> getCompetitions() { return competitions; }
    public String getTemplate() { return template; }
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
        return concat("", "Event{", "eventGroup=", nullsafe(()->eventGroup.getName()), ", name='", name, '\'', ", teams=", nullsafe(()->teams.size()), ", competitions=", nullsafe(()->competitions.size()), ", infos=" + getInfos() + '}');
    }

    public void overwrite(Event newEvent) {
        this.competitions = newEvent.competitions;
        this.eventGroup = newEvent.eventGroup;
        this.infos = newEvent.infos;
        this.name = newEvent.name;
        this.teams = newEvent.teams;
        this.template = newEvent.template;
    }
}