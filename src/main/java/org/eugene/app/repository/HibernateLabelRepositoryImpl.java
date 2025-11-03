package org.eugene.app.repository;

import lombok.extern.slf4j.Slf4j;
import org.eugene.app.model.Label;
import org.eugene.app.postgresql.SessionProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Slf4j
public class HibernateLabelRepositoryImpl implements LabelRepository {

    private static final String SELECT_ALL = """
            SELECT * FROM Label
            """;
    private static final String SELECT_BY_ID = """
            SELECT * FROM Label l
            where l.id = :id
            """;
    private static final String DELETE = """
            delete FROM Label l
            where l.id = :id
            """;
    private static final String UPDATE = """
            update Label l
            set l.name = :name
            where l.id = :id
            """;
    private static final String SELECT_LABELS_BY_POST_ID = """
            select l.id, l.name
            from Label l
            join Post p on p.id = l.post_id
            where p.id = :id
            """;

    @Override
    public Long create(Label label) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            try {
                s.persist(label);
                tx.commit();
                return label.getId();
            } catch (Exception e) {
                tx.rollback();
                log.error("Error creating label", e);
                return 0L;
            }
        }
    }

    @Override
    public Optional<Label> getById(Long id) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Label label = s.createQuery(SELECT_BY_ID, Label.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(label);
        } catch (Exception e) {
            log.error("Error fetching label by ID", e);
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
            log.info(deleted == 1 ? "Label deleted" : "No label found with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting label", e);
        }
    }

    @Override
    public void update(Label label) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            Transaction tx = s.beginTransaction();
            int updated = s.createQuery(UPDATE)
                    .setParameter("name", label.getName())
                    .setParameter("id", label.getId())
                    .executeUpdate();
            tx.commit();
            log.info(updated == 1 ? "Label updated" : "No label found with id: {}", label.getId());
        } catch (Exception e) {
            log.error("Error updating label", e);
        }
    }

    @Override
    public List<Label> getAll() {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            return s.createQuery(SELECT_ALL, Label.class).list();
        } catch (Exception e) {
            log.error("Error fetching all labels", e);
            return List.of();
        }
    }

    @Override
    public List<Label> getLabelsByPostId(Long postId) {
        try (Session s = SessionProvider.INSTANCE.getSession()) {
            return s.createQuery(SELECT_LABELS_BY_POST_ID, Label.class)
                    .setParameter("id", postId)
                    .list();
        } catch (Exception e) {
            log.error("Error fetching labels by post ID", e);
            return List.of();
        }
    }

}
