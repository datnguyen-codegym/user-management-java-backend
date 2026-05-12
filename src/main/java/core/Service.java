package core;

import java.util.Collection;
import java.util.Optional;

public interface Service<T> {
    Collection<T> list();
    Optional<T> findById();
    void delete(Long id);
    T update(T obj);
    T create(T obj);
}
