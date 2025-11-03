package org.eugene.app.service;

import lombok.AllArgsConstructor;
import org.eugene.app.model.Label;
import org.eugene.app.model.Post;
import org.eugene.app.repository.LabelRepository;
import org.eugene.app.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PostService {
    PostRepository postRepository;
    LabelRepository labelRepository;


    public Long create(Post post) {
        return postRepository.create(post);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public void update(Post post) {
        postRepository.update(post);
    }

    public Optional<Post> getById(Long id) {
        return postRepository.getById(id);
    }

    public void assignLabels(List<String> labels, Long postId) {
        postRepository.assignLabels(labels, postId);
    }

    public List<Label> getLabelsByPostId(Long postID) {
        return labelRepository.getLabelsByPostId(postID);
    }
}
