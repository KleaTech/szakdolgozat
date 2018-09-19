package hu.kleatech.jigsaw.persistence;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PersistenceImp implements Persistence<Object> {

    List<Object> repository = new ArrayList<>();

    @Override
    public void save(Object obj) {
        repository.add(obj);
    }

    @Override
    public void delete(Object equalsObj) {
        repository.remove(equalsObj);
    }

    @Override
    public Object get(Object equalsObj) {
        return repository.get(repository.indexOf(equalsObj));
    }

    @Override
    public List<Object> getAll() {
        return Collections.unmodifiableList(repository);
    }
}
