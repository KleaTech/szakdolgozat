package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.model.Sex;
import hu.kleatech.jigsaw.model.Team;
import java.time.LocalDate;
import java.util.Properties;
import org.springframework.format.annotation.DateTimeFormat;

class ObjectTransferHandler {
    
    static class ParticipantDTO {
        private String name;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private String sex;
        private Properties infos;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public LocalDate getBirthDate() { return birthDate; }
        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
        public String getSex() { return sex; }
        public void setSex(String sex) { this.sex = sex; }
        public Properties getInfos() { return infos; }
        public void setInfos(Properties infos) { this.infos = infos; }
               
        public Participant toEntity(Team team) {
            return new Participant(name, birthDate, Sex.valueOf(sex), team, infos);
        }
    }
}
