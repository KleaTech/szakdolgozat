package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.EventGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventGroupRepository extends JpaRepository<EventGroup, Long> {
}
