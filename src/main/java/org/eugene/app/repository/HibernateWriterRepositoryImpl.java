package org.eugene.app.repository;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.eugene.app.model.Writer;
import org.eugene.app.postgresql.SessionProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Slf4j
public class HibernateWriterRepositoryImpl implements WriterRepository {

    private static final String SELECT_ALL = """
            SELECT w FROM Writer w
            """;

    private static final String SELECT_BY_ID = """
            SELECT w FROM Writer w
            where w.id = :id
            """;
    private static final String DELETE = """
            delete FROM Writer w
            where w.id = :id
            """;
    private static final String UPDATE = """
            update Writer w
            set w.first_name = :firstName, w.last_name = :LastName
            where w.id = :id
            """;
    private static final String ASSIGN_WRITERS = """
            update Post p
            set p.writer_id = :writerId
            where p.id = :id
            """;

    @Override
    public Long create(Writer writer) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            try {
                s.persist(writer);
                tx.commit();

                return writer.getId();
            } catch (PersistenceException e) {
                if (tx != null && tx.getStatus().canRollback()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public Optional<Writer> getById(Long id) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Writer writer = s.createQuery(SELECT_BY_ID, Writer.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(writer);
        } catch (Exception e) {
            log.error("Find by ID failed", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int deleted = s.createQuery(DELETE)
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
            log.info(deleted == 1 ? "Writer deleted" : "No writer found by id: {}", id);
        } catch (Exception e) {
            log.error("Deleting by ID failed", e);
        }
    }

    @Override
    public void update(Writer writer) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int updated = s.createQuery(UPDATE)
                    .setParameter("firstName", writer.getFirstName())
                    .setParameter("lastName", writer.getLastName())
                    .setParameter("id", writer.getId())
                    .executeUpdate();
            tx.commit();
            log.info(updated == 1 ? "Writer updated" : "No writer found by id: {}", writer.getId());
        } catch (Exception e) {
            log.error("Updating writer failed", e);
        }
    }

    @Override
    public List<Writer> getAll() {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            return s.createQuery(SELECT_ALL, Writer.class).list();
        } catch (Exception e) {
            log.error("Error getting all writers from DB", e);
            return List.of();
        }
    }

    @Override
    public void assignPosts(List<String> posts, Long writerId) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int updatedCount = 0;
            for (String postIdStr : posts) {
                Long postId = Long.parseLong(postIdStr);
                int updated = s.createQuery(ASSIGN_WRITERS)
                        .setParameter("writerId", writerId)
                        .setParameter("postId", postId)
                        .executeUpdate();
                updatedCount += updated;
            }
            tx.commit();
            log.info(updatedCount == posts.size() ? "All posts assigned" : "Some posts were not updated");
        } catch (Exception e) {
            log.error("Assigning posts failed", e);
        }
    }

}
