package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
