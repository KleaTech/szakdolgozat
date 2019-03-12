package hu.kleatech.jigsaw.service.interfaces;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll();
    T add(T entity);
    void delete(T entity);
    T get(T entity);
    T get(Long id);
    T replace(T old, T nevv);
}
