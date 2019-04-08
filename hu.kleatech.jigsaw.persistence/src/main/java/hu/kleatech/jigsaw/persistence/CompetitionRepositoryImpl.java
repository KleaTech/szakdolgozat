package hu.kleatech.jigsaw.persistence;

import hu.kleatech.jigsaw.model.Competition;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CompetitionRepositoryImpl implements CompetitionRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void refresh(Competition entity) {
        em.refresh(entity);
    }
}
