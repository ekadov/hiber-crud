package org.eugene.app.tests;

import org.eugene.app.model.Post;
import org.eugene.app.model.Writer;
import org.eugene.app.repository.PostRepository;
import org.eugene.app.repository.WriterRepository;
import org.eugene.app.service.WriterService;
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
class WriterServiceTest {

    @Mock
    WriterRepository writerRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    WriterService writerService;

    @Test
    void create_shouldReturnWriterId() {
        Writer writer = new Writer().setFirstName("Ivan").setLastName("Ivanov");
        when(writerRepository.create(writer)).thenReturn(1L);

        Long result = writerService.create(writer);

        assertEquals(1L, result);
        verify(writerRepository).create(writer);
    }

    @Test
    void getAll_shouldReturnWritersWithPosts() {
        Writer writer = new Writer().setId(1L);
        List<Post> posts = List.of(new Post().setId(10L));
        when(writerRepository.getAll()).thenReturn(List.of(writer));
        when(postRepository.getPostsByWriterId(1L)).thenReturn(posts);

        List<Writer> result = writerService.getAll();

        assertEquals(1, result.size());
        assertEquals(posts, result.get(0).getPosts());
    }

    @Test
    void getById_shouldReturnWriterWithPosts() {
        Writer writer = new Writer().setId(1L);
        List<Post> posts = List.of(new Post().setId(10L));
        when(writerRepository.getById(1L)).thenReturn(Optional.of(writer));
        when(postRepository.getPostsByWriterId(1L)).thenReturn(posts);

        Optional<Writer> result = writerService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(posts, result.get().getPosts());
    }

    @Test
    void deleteById_shouldDelegateToRepository() {
        writerService.deleteById(1L);
        verify(writerRepository).deleteById(1L);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Writer writer = new Writer().setId(1L);
        writerService.update(writer);
        verify(writerRepository).update(writer);
    }

    @Test
    void assignPosts_shouldDelegateToRepository() {
        List<String> postIds = List.of("10", "20");
        writerService.assignPosts(postIds, 1L);
        verify(writerRepository).assignPosts(postIds, 1L);
    }

    @Test
    void getPostsByWriterId_shouldReturnPosts() {
        List<Post> posts = List.of(new Post().setId(10L));
        when(postRepository.getPostsByWriterId(1L)).thenReturn(posts);

        List<Post> result = writerService.getPostsByWriterId(1L);

        assertEquals(posts, result);
    }
}