package org.eugene.app.mainmodule;

import org.eugene.app.controller.LabelController;
import org.eugene.app.controller.PostController;
import org.eugene.app.controller.WriterController;
import org.eugene.app.repository.*;
import org.eugene.app.service.LabelService;
import org.eugene.app.service.PostService;
import org.eugene.app.service.WriterService;
import org.eugene.app.view.WriterView;

public class Main {

    public static void main(String[] args) {

        var w = new HibernateWriterRepositoryImpl()
                .getById(
                        1L
                );

        WriterRepository writerRepo = new HibernateWriterRepositoryImpl();
        PostRepository postRepo = new HibernatePostRepositoryImpl();
        LabelRepository labelRepo = new HibernateLabelRepositoryImpl();

        WriterService writerService = new WriterService(writerRepo, postRepo);
        PostService postService = new PostService(postRepo, labelRepo);
        LabelService labelService = new LabelService(labelRepo);

        WriterController writerController = new WriterController(writerService);
        PostController postController = new PostController(postService);
        LabelController labelController = new LabelController(labelService);

        WriterView view = new WriterView(writerController, postController, labelController);

        view.start();

    }
}
