package hu.kleatech.jigsaw.model;

import static hu.kleatech.jigsaw.utils.StringUtils.*;
import static hu.kleatech.jigsaw.utils.Utils.TryOrNull;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;

@Entity
public class Round {
    @ManyToOne
    @JoinColumn(name = "rounds")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "rounds_competition")
    private Competition competition;

    @Column(name = "values_")
    @Lob
    private ArrayList<Double> values = new ArrayList<>(3);

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected Round(){} //For serialization only
    public Round(Participant participant, Competition competition, Properties infos) {
        this.participant = participant;
        this.infos = infos;
        this.competition = competition;
    }

    public Participant getParticipant() { return participant; }
    public Competition getCompetition() { return competition; }
    public List<Double> getValues() {return Collections.unmodifiableList(values); }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round)) return false;
        Round round = (Round) o;
        return Objects.equals(getId(), round.getId());
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return concat("", "Round{", "team=", nullsafe(()->participant), ", competition=", nullsafe(()->competition.getName()), ", values=", values, ", infos=", getInfos());
    }

    public void overwrite(Round newRound) {
        this.competition = newRound.competition;
        this.infos = newRound.infos;
        this.participant = newRound.participant;
        this.values = newRound.values;
    }

    public Round add(Double... value) {
        values.addAll(Arrays.asList(value));
        return this;
    }
    public Round add(Collection<Double> values) {
        this.values.addAll(values);
        return this;
    }

    public double modify(int index, double newval) throws IndexOutOfBoundsException {
        return values.set(index, newval);
    }

    public List<Double> preresults(Function<List<Double>, List<Double>> logic) {
        return TryOrNull(() -> logic.apply(values));
    }

    //From values, not from preresults
    public Double result(Function<List<Double>, Double> logic) {
        return TryOrNull(() -> logic.apply(values));
    }
}