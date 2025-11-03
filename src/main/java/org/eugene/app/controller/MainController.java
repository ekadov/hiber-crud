package org.eugene.app.controller;

import org.eugene.app.repository.*;
import org.eugene.app.service.LabelService;
import org.eugene.app.service.PostService;
import org.eugene.app.service.WriterService;
import org.eugene.app.view.WriterView;

public class MainController {
    public static void start() {
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
