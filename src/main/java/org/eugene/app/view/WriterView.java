package org.eugene.app.view;

import org.eugene.app.controller.LabelController;
import org.eugene.app.controller.PostController;
import org.eugene.app.controller.WriterController;
import org.eugene.app.model.Label;
import org.eugene.app.model.Post;
import org.eugene.app.model.PostStatus;
import org.eugene.app.model.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WriterView {

    private final WriterController writerController;
    private final PostController postController;
    private final LabelController labelController;
    private final Scanner scanner = new Scanner(System.in);

    public WriterView(WriterController writerController, PostController postController, LabelController labelController) {
        this.writerController = writerController;
        this.postController = postController;
        this.labelController = labelController;
    }

    public void start() {
        while (true) {
            System.out.println("""
                    Главное меню:
                    1 - Работа с Writer
                    2 - Работа с Post
                    3 - Работа с Label
                    0 - Выход
                    """);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> writerMenu();
                case "2" -> postMenu();
                case "3" -> labelMenu();
                case "0" -> {
                    System.out.println("Завершение работы");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void writerMenu() {
        System.out.println("""
                Writer-меню:
                1 - Создать
                2 - Показать всех
                3 - Удалить по id
                4 - Обновить имя/фамилию
                5 - Назначить посты по id
                6 - Показать посты по WriterId
                7 - Показать Writer по Id
                0 - Назад
                """);
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Имя: ");
                String firstName = scanner.nextLine();
                System.out.print("Фамилия: ");
                String lastName = scanner.nextLine();
                writerController.create(firstName, lastName);
            }
            case "2" -> {
                List<Writer> writers = writerController.getAll();
                if (!writers.isEmpty()) {
                    writers.forEach(w -> {
                        System.out.println("Writer: id=" + w.getId() + ", " + "\"" + w.getFirstName() + " " + w.getLastName() + "\"");
                        if (!w.getPosts().isEmpty()) {
                            for (int i = 0; i < w.getPosts().size(); i++) {
                                System.out.println("Post #" + i + ", ID = " + w.getPosts().get(i).getId() +
                                        ", Created: " + w.getPosts().get(i).getCreated());
                            }
                        }
                    });
                } else {
                    System.out.println("Нет записей");
                }
            }
            case "3" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                writerController.deleteById(id);
            }
            case "4" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                System.out.print("Новое имя: ");
                String firstName = scanner.nextLine();
                System.out.print("Новая фамилия: ");
                String lastName = scanner.nextLine();
                writerController.update(id, firstName, lastName);
            }
            case "5" -> {
                System.out.print("Введите writerId: ");
                Long writerId = Long.parseLong(scanner.nextLine());

                System.out.println("Доступные посты:");
                postController.getAll().forEach(p ->
                        System.out.println("Post: id=" + p.getId() + ", " + "\"" + p.getCreated() + " " + p.getStatus()
                                + "\"")
                );

                System.out.print("Введите id постов через запятую: ");
                String[] ids = scanner.nextLine().split(",");

                if (ids.length != 0) {
                    writerController.assignPosts(writerId, Arrays.asList(ids));
                }
            }
            case "6" -> {
                System.out.print("Введите writerId: ");
                Long writerId = Long.parseLong(scanner.nextLine());
                List<Post> posts = writerController.getPostsByWriterId(writerId);
                if (!posts.isEmpty()) {
                    posts.forEach(p -> {
                        System.out.println("Post: id=" + p.getId() + ", " + "\"" + p.getCreated() + " " + p.getStatus()
                                + "\"");
                    });
                } else {
                    System.out.println("Нет записей");
                }
            }
            case "7" -> {
                System.out.print("Введите Writer id: ");
                Long writerId = Long.parseLong(scanner.nextLine());
                var writer = writerController.getById(writerId);
                if (writer.isPresent()) {
                    System.out.println("Writer id=" + writer.get().getId());
                    System.out.println("First Name: " + writer.get().getFirstName());
                    System.out.println("Last Name: " + writer.get().getLastName());
                    if (!writer.get().getPosts().isEmpty()) {
                        for (int i = 0; i < writer.get().getPosts().size(); i++) {
                            System.out.println("Post #" + i + ", ID = " + writer.get().getPosts().get(i).getId() +
                                    ", Created: " + writer.get().getPosts().get(i).getCreated());
                        }
                    }
                } else {
                    System.out.println("Не найден Writer");
                }
            }
        }
    }

    private void postMenu() {
        System.out.println("""
                Post-меню:
                1 - Создать
                2 - Показать все
                3 - Удалить
                4 - Обновить
                5 - Назначить Label
                6 - Показать Label по Post id
                7 - Показать Post по id
                0 - Назад
                """);
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                Post post = new Post();
                List<Label> labels = new ArrayList<>();

                System.out.print("Введите Контент: ");
                String content = scanner.nextLine();

                System.out.print("Сколько Label хотите создать для поста: ");
                String labelCount = scanner.nextLine();

                for (int i = 0; i < Integer.parseInt(labelCount); i++) {
                    System.out.print("Введите Название Label: ");
                    String labelName = scanner.nextLine();
                    labels.add(new Label().setName(labelName));
                }
                post.setContent(content).setLabels(labels);

                postController.create(post);
            }
            case "2" -> {
                var posts = postController.getAll();
                if (!posts.isEmpty()) {
                    posts.forEach(p -> {
                        System.out.println("Post id=" + p.getId());
                        System.out.println("Content: ");
                        if (p.getContent().length() > 10) {
                            System.out.println(p.getContent().substring(0, 10));
                        } else {
                            System.out.println(p.getContent());
                        }
                        System.out.println("Created: " + p.getCreated());
                    });
                } else {
                    System.out.println("Нет записей");
                }
            }
            case "3" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                postController.deleteById(id);
            }
            case "4" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                System.out.print("Новый контент: ");
                String content = scanner.nextLine();
                System.out.print("Новый статус: ");
                String status = scanner.nextLine();
                postController.update(id, content, PostStatus.valueOf(status));
            }
            case "5" -> {
                System.out.print("Введите postId: ");
                Long writerId = Long.parseLong(scanner.nextLine());

                System.out.println("Доступные label:");
                labelController.getAll().forEach(p ->
                        System.out.println("Label: id=" + p.getId() + ", " + "\"" + p.getName() + "\"")
                );

                System.out.print("Введите id Label через запятую: ");
                String[] ids = scanner.nextLine().split(",");

                if (ids.length != 0) {
                    postController.assignLabels(writerId, Arrays.asList(ids));
                }
            }
            case "6" -> {
                System.out.print("Введите Post id: ");
                Long postId = Long.parseLong(scanner.nextLine());
                List<Label> posts = postController.getLabelByPostId(postId);
                if (!posts.isEmpty()) {
                    posts.forEach(p -> {
                        System.out.println("Label: id=" + p.getId() + ", " + "\"" + p.getName() + "\"");
                    });
                } else {
                    System.out.println("Нет записей");
                }
            }
            case "7" -> {
                System.out.print("Введите Post id: ");
                Long postId = Long.parseLong(scanner.nextLine());
                var post = postController.getById(postId);
                if (post.isPresent()) {
                    System.out.println("Post id=" + post.get().getId());
                    if (post.get().getContent().length() > 10) {
                        System.out.println("Content: ");
                        System.out.println(post.get().getContent().substring(0, 10));
                    } else {
                        System.out.println("Content: ");
                        System.out.println(post.get().getContent());
                    }
                    System.out.println("Created: " + post.get().getCreated());
                    System.out.println("Updated: " + post.get().getUpdated());
                    System.out.println("Status: " + post.get().getStatus());
                } else {
                    System.out.println("Не найден Пост");
                }
            }
        }
    }

    private void labelMenu() {
        System.out.println("""
                Label-меню:
                1 - Создать
                2 - Показать все
                3 - Удалить
                4 - Обновить
                5 - Показать Label по Id
                0 - Назад
                """);
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Название: ");
                String name = scanner.nextLine();
                labelController.create(name);
            }
            case "2" -> {
                var labels = labelController.getAll();
                if (!labels.isEmpty()) {
                    labels.forEach(l ->
                            System.out.println("Label id=" + l.getId() + ", name = \"" + l.getName() + "\"")
                    );
                } else {
                    System.out.println("Нет записей");
                }
            }
            case "3" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                labelController.deleteById(id);
            }
            case "4" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                System.out.print("Новое название: ");
                String name = scanner.nextLine();
                labelController.update(id, name);
            }
            case "5" -> {
                System.out.print("Введите id: ");
                Long id = Long.parseLong(scanner.nextLine());
                var label = labelController.getById(id);
                if (label.isPresent()) {
                    System.out.println("Label id=" + label.get().getId() + ", name = \"" + label.get().getName() + "\"");
                } else {
                    System.out.println("Нет записей");
                }
            }
        }
    }

}
