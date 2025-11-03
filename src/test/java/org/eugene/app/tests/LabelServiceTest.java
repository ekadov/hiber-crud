package org.eugene.app.tests;

import org.eugene.app.model.Label;
import org.eugene.app.repository.LabelRepository;
import org.eugene.app.service.LabelService;
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
class LabelServiceTest {

    @Mock
    LabelRepository labelRepository;

    @InjectMocks
    LabelService labelService;

    @Test
    void create_shouldDelegateToRepository() {
        Label label = new Label().setName("Ivan");
        labelService.create(label);
        verify(labelRepository).create(label);
    }

    @Test
    void getAll_shouldReturnLabels() {
        List<Label> labels = List.of(new Label().setId(1L));
        when(labelRepository.getAll()).thenReturn(labels);

        List<Label> result = labelService.getAll();

        assertEquals(labels, result);
    }

    @Test
    void getById_shouldReturnLabel() {
        Label label = new Label().setId(1L);
        when(labelRepository.getById(1L)).thenReturn(Optional.of(label));

        Optional<Label> result = labelService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(label, result.get());
    }

    @Test
    void deleteById_shouldDelegateToRepository() {
        labelService.deleteById(1L);
        verify(labelRepository).deleteById(1L);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Label label = new Label().setId(1L).setName("Label 1");
        labelService.update(label);
        verify(labelRepository).update(label);
    }
}
