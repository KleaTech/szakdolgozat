package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
