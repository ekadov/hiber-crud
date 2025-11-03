package org.eugene.app.service;

import lombok.AllArgsConstructor;
import org.eugene.app.model.Label;
import org.eugene.app.repository.LabelRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LabelService {
    LabelRepository labelRepository;

    public void create(Label label) {
        labelRepository.create(label);
    }

    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public void deleteById(Long id) {
        labelRepository.deleteById(id);
    }

    public void update(Label label) {
        labelRepository.update(label);
    }

    public Optional<Label> getById(Long id) {
        return labelRepository.getById(id);
    }
}
