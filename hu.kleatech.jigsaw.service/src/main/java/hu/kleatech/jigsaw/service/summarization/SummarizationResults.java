package hu.kleatech.jigsaw.service.summarization;

import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.utils.ListMap;

public final class SummarizationResults {
    private ListMap<Team, Double> byTeam;
    private ListMap<Participant, Double> byParticipant;
    private ListMap<Event, ListMap<Team, Double>> byEventByTeam;
    private ListMap<Event, ListMap<Participant, Double>> byEventByParticipant;
    private ListMap<Competition, ListMap<Team, Double>> byCompetitionByTeam;
    private ListMap<Competition, ListMap<Participant, Double>> byCompetitionByParticipant;

    public ListMap<Team, Double> getByTeam() { return byTeam; }
    public ListMap<Participant, Double> getByParticipant() { return byParticipant; }
    public ListMap<Event, ListMap<Team, Double>> getByEventByTeam() { return byEventByTeam; }
    public ListMap<Event, ListMap<Participant, Double>> getByEventByParticipant() { return byEventByParticipant; }
    public ListMap<Competition, ListMap<Team, Double>> getByCompetitionByTeam() { return byCompetitionByTeam; }
    public ListMap<Competition, ListMap<Participant, Double>> getByCompetitionByParticipant() { return byCompetitionByParticipant; }

    void setByTeam(ListMap<Team, Double> byTeam) { this.byTeam = byTeam; }
    void setByParticipant(ListMap<Participant, Double> byParticipant) { this.byParticipant = byParticipant; }
    void setByEventByTeam(ListMap<Event, ListMap<Team, Double>> byEventByTeam) { this.byEventByTeam = byEventByTeam; }
    void setByEventByParticipant(ListMap<Event, ListMap<Participant, Double>> byEventByParticipant) { this.byEventByParticipant = byEventByParticipant; }
    void setByCompetitionByTeam(ListMap<Competition, ListMap<Team, Double>> byCompetitionByTeam) { this.byCompetitionByTeam = byCompetitionByTeam; }
    void setByCompetitionByParticipant(ListMap<Competition, ListMap<Participant, Double>> byCompetitionByParticipant) { this.byCompetitionByParticipant = byCompetitionByParticipant; }

    @Override
    public String toString() {
        return "SummarizationResults{\n\t" + 
                "byTeam=" + byTeam + 
                ", \n\tbyParticipant=" + byParticipant + 
                ", \n\tbyEventByTeam=" + byEventByTeam + 
                ", \n\tbyEventByParticipant=" + byEventByParticipant + 
                ", \n\tbyCompetitionByTeam=" + byCompetitionByTeam + 
                ", \n\tbyCompetitionByParticipant=" + byCompetitionByParticipant + 
                "\n}";
    }
    
    
}
