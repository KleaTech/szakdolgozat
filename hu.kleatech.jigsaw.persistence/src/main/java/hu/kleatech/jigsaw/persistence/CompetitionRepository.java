package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long>, CompetitionRepositoryCustom {
}