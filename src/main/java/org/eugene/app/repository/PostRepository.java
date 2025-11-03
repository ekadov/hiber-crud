package org.eugene.app.repository;

import org.eugene.app.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends GenericRepository<Post, Long> {
    @Override
    Long create(Post post);

    @Override
    Optional<Post> getById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    void update(Post post);

    @Override
    List<Post> getAll();

    void assignLabels(List<String> labels, Long postId);

    List<Post> getPostsByWriterId(Long writerId);
}
