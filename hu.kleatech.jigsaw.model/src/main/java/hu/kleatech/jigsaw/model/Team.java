package hu.kleatech.jigsaw.model;

import static hu.kleatech.jigsaw.utils.StringUtils.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Entity
public class Team implements Serializable {
    @ManyToOne
    @JoinColumn(name="associatedTeams")
    private EventGroup associatedEventGroup;
    
    @Transient
    private List<Event> events;

    @Transient
    private List<Competition> competitions;

    @Column
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Participant> participants;

    @Transient
    private List<Round> rounds;

    @Column
    @Lob
    private Properties infos;
    
    @Column
    private String template;

    @Id
    @GeneratedValue
    private Long id;

    protected Team(){} //For serialization only
    public Team(String name, String template, EventGroup associatedEventGroup, Properties infos) {
        this.name = name;
        this.infos = infos;
        this.template = template;
        this.associatedEventGroup = associatedEventGroup;
    }

    @PostLoad
    private void onLoad(){
        this.rounds = getParticipants().stream().map(p -> p.getRounds()).flatMap(r -> r.stream()).collect(Collectors.toList());
        this.competitions = getRounds().stream().map(r -> r.getCompetition()).collect(Collectors.toList());
        this.events = getCompetitions().stream().map(c -> c.getEvent()).collect(Collectors.toList());
    }

    public List<Event> getEvents() { return events; }
    public List<Competition> getCompetitions() { return competitions; }
    public String getName() { return name; }
    public List<Participant> getParticipants() { return participants; }
    public List<Round> getRounds() { return rounds; }
    public String getTemplate() { return template; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }
    public EventGroup getAssociatedEventGroup() { return associatedEventGroup; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return Objects.equals(getId(), team.getId());
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return concat("", "Team{", "events=", nullsafe(()->events.size()), ", competitions=", nullsafe(()->competitions.size()), ", name='", name, '\'', ", participants=", nullsafe(()->participants), ", rounds=", nullsafe(()->rounds.size()), ", infos=", getInfos(), '}');
    }

    public void overwrite(Team newTeam) {
        this.competitions = newTeam.competitions;
        this.events =  newTeam.events;
        this.infos = newTeam.infos;
        this.name = newTeam.name;
        this.participants = newTeam.participants;
        this.rounds = newTeam.rounds;
        this.associatedEventGroup = newTeam.associatedEventGroup;
    }
}
