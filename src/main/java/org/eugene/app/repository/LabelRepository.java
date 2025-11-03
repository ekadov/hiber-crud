package org.eugene.app.repository;

import org.eugene.app.model.Label;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends GenericRepository<Label, Long> {
    @Override
    Long create(Label post);

    @Override
    Optional<Label> getById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    void update(Label post);

    @Override
    List<Label> getAll();

    List<Label> getLabelsByPostId(Long postId);
}
