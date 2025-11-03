package org.eugene.app.repository;

import lombok.extern.slf4j.Slf4j;
import org.eugene.app.model.Post;
import org.eugene.app.postgresql.SessionProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Slf4j
public class HibernatePostRepositoryImpl implements PostRepository {

    private static final String SELECT_ALL = """
            select p.id as Post_id, p.content, p.created, p.updated, p.status, l.id as Label_id, l.name as Label_Name
            from Post p
            left join Label l
            on l.post_id = p.id
            where p.status != 'DELETED'
            """;
    private static final String SELECT_BY_ID = """
            select p.id as Post_id, p.content, p.created, p.updated, p.status, l.id as Label_id, l.name as Label_Name
            from Post p
            left join Label l
            on l.post_id = p.id
            where p.id = :id and p.status != 'DELETED'
            """;
    private static final String DELETE = """
            UPDATE Post p
            SET p.status = 'DELETED'
            WHERE id = :id
            """;
    private static final String UPDATE = """
            update Post p
            set p.content = :content, p.updated = :updated, p.status = :status
            where p.id = :id
            """;
    private static final String ASSIGN_LABELS = """
            update Label l
            set l.post_id = :postId
            where l.id = :id
            """;
    private static final String SELECT_POSTS_BY_WRITER_ID = """
            select p.id, p.created, p.status
            from Post p
            join Writer w on w.id = p.writer_id
            where w.id = :id
            """;

    @Override
    public Long create(Post post) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            try {
                s.persist(post);
                tx.commit();
                return post.getId();
            } catch (Exception e) {
                tx.rollback();
                log.error("Error creating post", e);
                return 0L;
            }
        }
    }

    @Override
    public Optional<Post> getById(Long id) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Post post = s.createQuery(SELECT_BY_ID, Post.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(post);
        } catch (Exception e) {
            log.error("Error fetching post by ID", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int updated = s.createQuery(DELETE)
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
            log.info(updated == 1 ? "Post deleted" : "No post found with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting post", e);
        }
    }

    @Override
    public void update(Post post) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int updated = s.createQuery(UPDATE)
                    .setParameter("content", post.getContent())
                    .setParameter("updated", post.getUpdated())
                    .setParameter("status", post.getStatus())
                    .setParameter("id", post.getId())
                    .executeUpdate();
            tx.commit();
            log.info(updated == 1 ? "Post updated" : "No post found with id: {}", post.getId());
        } catch (Exception e) {
            log.error("Error updating post", e);
        }
    }

    @Override
    public List<Post> getAll() {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            return s.createQuery(SELECT_ALL, Post.class).list();
        } catch (Exception e) {
            log.error("Error fetching all posts", e);
            return List.of();
        }
    }

    @Override
    public void assignLabels(List<String> labels, Long postId) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int updatedCount = 0;
            for (String labelIdStr : labels) {
                Long labelId = Long.parseLong(labelIdStr);
                int updated = s.createQuery(ASSIGN_LABELS)
                        .setParameter("postId", postId)
                        .setParameter("id", labelId)
                        .executeUpdate();
                updatedCount += updated;
            }
            tx.commit();
            log.info(updatedCount == labels.size() ? "All labels assigned" : "Some labels were not updated");
        } catch (Exception e) {
            log.error("Error assigning labels", e);
        }
    }

    @Override
    public List<Post> getPostsByWriterId(Long writerId) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            return s.createQuery(SELECT_POSTS_BY_WRITER_ID, Post.class)
                    .setParameter("id", writerId)
                    .list();
        } catch (Exception e) {
            log.error("Error fetching posts by writer ID", e);
            return List.of();
        }
    }

}
