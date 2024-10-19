package todo.list.application.database.repository;

import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface SimpleRepository<T> {

    List<T> findAll();

    T findById(long id);

    T saveOrUpdate(T t);

    void deleteById(long id);

    void eraseById(long id);

    void clear();
}
