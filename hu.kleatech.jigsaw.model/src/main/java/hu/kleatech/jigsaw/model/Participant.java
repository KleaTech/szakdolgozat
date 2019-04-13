package hu.kleatech.jigsaw.model;

import static hu.kleatech.jigsaw.utils.StringUtils.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Entity
public class Participant implements Serializable {

    @Column
    private String name;

    @Column
    private LocalDate birthDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column
    @Lob
    private Properties infos;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Round> rounds;

    @ManyToOne
    @JoinColumn(name = "participants")
    private Team team;

    @Id
    @GeneratedValue
    private Long id;

    protected Participant(){} //For serialization only
    public Participant(String name, LocalDate birthDate, Sex sex, Team team, Properties infos) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.infos = infos;
        this.team = team;
    }

    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public Sex getSex() { return sex; }
    public Properties getInfos() { return infos==null?new Properties():infos; }
    public Team getTeam() { return team; }
    public List<Round> getRounds() { return rounds==null?Collections.emptyList():rounds; }
    public Long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return Objects.equals(getId(), that.getId());
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return concat("", "Participant{", "name='", name, '\'', ", birthDate=", birthDate, ", sex=", sex, ", infos=", getInfos(), ", team=", nullsafe(()->team.getName()), '}');
    }

    public void overwrite(Participant newParticipant) {
        this.birthDate = newParticipant.birthDate;
        this.infos = newParticipant.infos;
        this.name = newParticipant.name;
        this.sex = newParticipant.sex;
        this.team = newParticipant.team;
        this.rounds = newParticipant.rounds;
    }
}
