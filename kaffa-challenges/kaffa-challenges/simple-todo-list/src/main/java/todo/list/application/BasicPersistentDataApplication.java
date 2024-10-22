package todo.list.application;

import java.util.List;

public interface BasicPersistentDataApplication<T> {

    List<T> all();

    T create(T t);

    T update(T t);

    void delete(T t);

    void deleteThese(List<T> ts);

    void clear();

}
