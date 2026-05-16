package core;

import java.util.Collection;
import java.util.Optional;

public interface Repo<T, ID> {
    Collection<T> list();
    Optional<T> findById(ID id);
    void delete(ID id);
    T update(T obj);
    T create(T obj);
}
