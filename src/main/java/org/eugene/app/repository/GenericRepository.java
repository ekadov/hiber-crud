package org.eugene.app.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    ID create(T t);

    Optional<T> getById(Long id);

    void deleteById(Long id);

    void update(T t);

    List<T> getAll();

}
