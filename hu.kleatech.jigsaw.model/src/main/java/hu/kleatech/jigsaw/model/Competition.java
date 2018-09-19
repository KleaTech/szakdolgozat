package hu.kleatech.jigsaw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Entity
public class Competition implements Serializable {

    @ManyToOne
    @JoinColumn(name = "competitions")
    private Event event;

    @Column
    private String name;

    @OneToMany(mappedBy = "competition")
    private List<Round> rounds;

    @Transient
    private List<Team> teams;

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected Competition(){} //For serialization only
    public Competition(Event event, String name, List<Round> rounds, List<Team> teams, Properties infos) {
        this.event = event;
        this.name = name;
        this.rounds = rounds;
        this.teams = teams;
        this.infos = infos;
    }

    public Event getEvent() { return event; }
    public String getName() { return name; }
    public List<Round> getRounds() { return rounds; }
    public List<Team> getTeams() { return teams; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competition)) return false;
        Competition that = (Competition) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() { return "Competition{" + "event=" + event.getName() + ", name='" + name + '\'' + ", rounds=" + rounds.size() + ", teams=" + teams.size() + ", infos=" + infos.size() + '}'; }

    public void overwrite(Competition newCompetition) {
        this.event = newCompetition.event;
        this.infos = newCompetition.infos;
        this.name = newCompetition.name;
        this.rounds = newCompetition.rounds;
        this.teams = newCompetition.teams;
    }
}
