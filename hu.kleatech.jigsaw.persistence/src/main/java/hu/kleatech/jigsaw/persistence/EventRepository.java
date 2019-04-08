package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
