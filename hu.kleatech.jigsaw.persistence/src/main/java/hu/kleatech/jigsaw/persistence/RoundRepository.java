package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}