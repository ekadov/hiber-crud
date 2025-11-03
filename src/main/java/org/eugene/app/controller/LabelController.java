package org.eugene.app.controller;

import lombok.AllArgsConstructor;
import org.eugene.app.model.Label;
import org.eugene.app.service.LabelService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LabelController {

    private final LabelService labelService;

    public void create(String name) {
        Label label = new Label();
        label.setName(name);

        labelService.create(label);
    }

    public List<Label> getAll() {
        return labelService.getAll();
    }

    public void deleteById(Long id) {
        labelService.deleteById(id);
    }

    public void update(Long id, String name) {
        Label label = new Label();
        label
                .setId(id)
                .setName(name);

        labelService.update(label);
    }

    public Optional<Label> getById(Long id) {
        return labelService.getById(id);
    }

}
