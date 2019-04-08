package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoundRepository extends JpaRepository<Round, Long> {
    @Modifying
    @Query("delete from Round t where t.id = ?1")
    void delete(Long id);
}