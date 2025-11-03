package org.eugene.app.controller;

import lombok.AllArgsConstructor;
import org.eugene.app.model.Label;
import org.eugene.app.model.Post;
import org.eugene.app.model.PostStatus;
import org.eugene.app.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PostController {

    private final PostService postService;

    public Long create(Post post) {
        List<Label> labels = new ArrayList<>();
        post
                .setCreated(LocalDateTime.now())
                .setContent(post.getContent())
                .setLabels(labels)
                .setStatus(PostStatus.UNDER_REVIEW);

        return postService.create(post);
    }

    public List<Post> getAll() {
        return postService.getAll();
    }

    public void deleteById(Long id) {
        postService.deleteById(id);
    }

    public void update(Long id, String content, PostStatus postStatus) {
        Post post = new Post();
        post
                .setId(id)
                .setUpdated(LocalDateTime.now())
                .setContent(content)
                .setStatus(postStatus);

        postService.update(post);
    }

    public Optional<Post> getById(Long id) {
        return postService.getById(id);
    }

    public void assignLabels(Long postId, List<String> labels) {
        if (!labels.isEmpty()) {
            postService.assignLabels(labels, postId);
        }
    }

    public List<Label> getLabelByPostId(Long postID) {
        return postService.getLabelsByPostId(postID);
    }
}
