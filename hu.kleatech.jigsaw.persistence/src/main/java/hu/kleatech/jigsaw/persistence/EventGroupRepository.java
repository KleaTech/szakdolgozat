package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.EventGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGroupRepository extends JpaRepository<EventGroup, Long> {
}