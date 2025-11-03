package org.eugene.app.controller;

import lombok.AllArgsConstructor;
import org.eugene.app.model.Post;
import org.eugene.app.model.Writer;
import org.eugene.app.service.WriterService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class WriterController {

    private final WriterService writerService;

    public Long create(String firstName, String lastName) {
        Writer writer = new Writer();
        writer
                .setFirstName(firstName)
                .setLastName(lastName);

        return writerService.create(writer);
    }

    public List<Writer> getAll() {
        return writerService.getAll();
    }

    public void deleteById(Long id) {
        writerService.deleteById(id);
    }

    public void update(Long id, String firstName, String lastName) {
        Writer writer = new Writer();
        writer
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName);

        writerService.update(writer);
    }

    public Optional<Writer> getById(Long id) {
        return writerService.getById(id);
    }

    public void assignPosts(Long writerId, List<String> posts) {
        if (!posts.isEmpty()) {
            writerService.assignPosts(posts, writerId);
        }
    }

    public List<Post> getPostsByWriterId(Long writerId) {
        return writerService.getPostsByWriterId(writerId);
    }
}
