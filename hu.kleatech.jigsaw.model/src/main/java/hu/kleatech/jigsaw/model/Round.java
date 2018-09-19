package hu.kleatech.jigsaw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Entity
public class Round {
    @Column
    @ManyToOne
    @JoinColumn(name = "rounds")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "rounds")
    private Competition competition;

    @Column
    @Lob
    private List<Double> values = new ArrayList<>(3);

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected Round(){} //For serialization only
    public Round(Team team, Competition competition, List<Double> values, Properties infos) {
        this.team = team;
        this.competition = competition;
        this.values = values;
        this.infos = infos;
    }

    public Team getTeam() { return team; }
    public Competition getCompetition() { return competition; }
    public List<Double> getValues() { return values; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round)) return false;
        Round round = (Round) o;
        return Objects.equals(id, round.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return "Round{" + "team=" + team + ", competition=" + competition.getName() + ", values=" + values + ", infos=" + infos.size() + '}'; }

    public void overwrite(Round newRound) {
        this.competition = newRound.competition;
        this.infos = newRound.infos;
        this.team = newRound.team;
        this.values = newRound.values;
    }
}
