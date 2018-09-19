package hu.kleatech.jigsaw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Entity
public class Team implements Serializable {
    @Transient
    private List<Event> events;

    @Transient
    private List<Competition> competitions;

    @Column
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Participant> participants;

    @OneToMany(mappedBy = "team")
    private List<Round> rounds;

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected Team(){} //For serialization only
    public Team(List<Event> events, List<Competition> competitions, String name, List<Participant> participants, List<Round> rounds, Properties infos) {
        this.events = events;
        this.competitions = competitions;
        this.name = name;
        this.participants = participants;
        this.rounds = rounds;
        this.infos = infos;
    }

    public List<Event> getEvents() { return events; }
    public List<Competition> getCompetitions() { return competitions; }
    public String getName() { return name; }
    public List<Participant> getParticipants() { return participants; }
    public List<Round> getRounds() { return rounds; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() { return "Team{" + "events=" + events.size() + ", competitions=" + competitions.size() + ", name='" + name + '\'' + ", participants=" + participants + ", rounds=" + rounds.size() + ", infos=" + infos.size() + '}'; }

    public void overwrite(Team newTeam) {
        this.competitions = newTeam.competitions;
        this.events =  newTeam.events;
        this.infos = newTeam.infos;
        this.name = newTeam.name;
        this.participants = newTeam.participants;
        this.rounds = newTeam.rounds;
    }
}
