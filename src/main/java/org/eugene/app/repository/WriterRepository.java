package org.eugene.app.repository;

import org.eugene.app.model.Writer;

import java.util.List;
import java.util.Optional;

public interface WriterRepository extends GenericRepository<Writer, Long> {

    @Override
    Long create(Writer writer);

    @Override
    Optional<Writer> getById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    void update(Writer writer);

    @Override
    List<Writer> getAll();

    void assignPosts(List<String> posts, Long writerId);
}
