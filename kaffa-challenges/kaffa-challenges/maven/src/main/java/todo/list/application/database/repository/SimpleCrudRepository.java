package todo.list.application.database.repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface SimpleCrudRepository<T> {

    List<T> findAll();

    Optional<T> findById(long id);

    T save(T t);

    T update(T t);

    T saveOrUpdate(T t);

    void deleteById(long id);

    void eraseById(long id);

    void clear();
}
