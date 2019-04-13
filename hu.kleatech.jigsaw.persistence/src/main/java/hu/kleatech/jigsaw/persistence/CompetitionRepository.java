package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Competition;
import hu.kleatech.jigsaw.model.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long>, CompetitionRepositoryCustom {
}