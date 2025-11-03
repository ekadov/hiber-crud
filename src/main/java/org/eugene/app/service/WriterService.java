package org.eugene.app.service;

import lombok.AllArgsConstructor;
import org.eugene.app.model.Post;
import org.eugene.app.model.Writer;
import org.eugene.app.repository.PostRepository;
import org.eugene.app.repository.WriterRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class WriterService {
    WriterRepository writerRepository;
    PostRepository postRepository;

    public Long create(Writer writer) {
        return writerRepository.create(writer);
    }

    public List<Writer> getAll() {
        var writersList = writerRepository.getAll();

        if (!writersList.isEmpty()) {
            writersList.forEach(w ->
                    w.setPosts(getPostsByWriterId(w.getId())));
        }
        return writersList;
    }

    public void deleteById(Long id) {
        writerRepository.deleteById(id);
    }

    public void update(Writer writer) {
        writerRepository.update(writer);
    }

    public Optional<Writer> getById(Long id) {
        var writer = writerRepository.getById(id);
        writer.ifPresent(value -> value.setPosts(postRepository.getPostsByWriterId(id)));

        return writer;
    }

    public void assignPosts(List<String> posts, Long writerId) {
        writerRepository.assignPosts(posts, writerId);
    }

    public List<Post> getPostsByWriterId(Long writerId) {
        return postRepository.getPostsByWriterId(writerId);
    }
}
