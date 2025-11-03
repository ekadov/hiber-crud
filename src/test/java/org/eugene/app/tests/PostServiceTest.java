package org.eugene.app.tests;

import org.eugene.app.model.Label;
import org.eugene.app.model.Post;
import org.eugene.app.repository.LabelRepository;
import org.eugene.app.repository.PostRepository;
import org.eugene.app.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    LabelRepository labelRepository;

    @InjectMocks
    PostService postService;

    @Test
    void create_shouldReturnPostId() {
        Post post = new Post().setContent("New post");
        when(postRepository.create(post)).thenReturn(100L);

        Long result = postService.create(post);

        assertEquals(100L, result);
        verify(postRepository).create(post);
    }

    @Test
    void getAll_shouldReturnPosts() {
        List<Post> posts = List.of(new Post().setId(1L));
        when(postRepository.getAll()).thenReturn(posts);

        List<Post> result = postService.getAll();

        assertEquals(posts, result);
    }

    @Test
    void getById_shouldReturnPost() {
        Post post = new Post().setId(1L);
        when(postRepository.getById(1L)).thenReturn(Optional.of(post));

        Optional<Post> result = postService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(post, result.get());
    }

    @Test
    void deleteById_shouldDelegateToRepository() {
        postService.deleteById(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Post post = new Post().setId(1L);
        postService.update(post);
        verify(postRepository).update(post);
    }

    @Test
    void assignLabels_shouldDelegateToRepository() {
        List<String> labelIds = List.of("5", "6");
        postService.assignLabels(labelIds, 1L);
        verify(postRepository).assignLabels(labelIds, 1L);
    }

    @Test
    void getLabelsByPostId_shouldReturnLabels() {
        List<Label> labels = List.of(new Label().setId(1L));
        when(labelRepository.getLabelsByPostId(1L)).thenReturn(labels);

        List<Label> result = postService.getLabelsByPostId(1L);

        assertEquals(labels, result);
    }
}
