package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
