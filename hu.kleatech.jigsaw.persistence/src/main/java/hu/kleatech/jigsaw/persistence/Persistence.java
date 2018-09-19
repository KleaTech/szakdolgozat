package hu.kleatech.jigsaw.persistence;

import java.util.List;

public interface Persistence<T> {
    void save(T obj);
    void delete(T equalsObj);
    T get(T equalsObj);
    List<T> getAll();
}
