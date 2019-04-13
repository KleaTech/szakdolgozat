package hu.kleatech.jigsaw.model;

import static hu.kleatech.jigsaw.utils.StringUtils.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Entity
public class Competition implements Serializable {

    @ManyToOne
    @JoinColumn(name = "competitions")
    private Event event;

    @Column
    private String name;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    @Column(name = "rounds_competition")
    private List<Round> rounds = Collections.EMPTY_LIST;

    @Transient
    private List<Team> teams;

    @Column
    private String template;

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected Competition(){} //For serialization only
    public Competition(Event event, String name, String template, Properties infos) {
        this.event = event;
        this.name = name;
        this.infos = infos;
        this.template = template;
    }

    @PostLoad
    private void onLoad(){
        this.teams = getRounds().stream().map(r -> r.getParticipant().getTeam()).collect(Collectors.toList());
    }

    public Event getEvent() { return event; }
    public String getName() { return name; }
    public List<Round> getRounds() { return rounds; }
    public List<Team> getTeams() { if(teams==null) onLoad(); return teams; }
    public String getTemplate() { return template; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competition)) return false;
        Competition that = (Competition) o;
        return Objects.equals(getId(), that.getId());
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return concat("", "Competition{", "event=", nullsafe(()->event.getName()), ", name='", name, '\'', ", rounds=", nullsafe(()->rounds.size()), ", teams=", nullsafe(()->teams.size()), ", infos=" + getInfos(), '}');
    }

    public void overwrite(Competition newCompetition) {
        this.event = newCompetition.event;
        this.infos = newCompetition.infos;
        this.name = newCompetition.name;
        this.rounds = newCompetition.rounds;
        this.teams = newCompetition.teams;
    }
}