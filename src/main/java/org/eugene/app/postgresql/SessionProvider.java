package org.eugene.app.postgresql;

import org.eugene.app.model.Label;
import org.eugene.app.model.Post;
import org.eugene.app.model.Writer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.util.Properties;

public enum SessionProvider {
    INSTANCE;

    private SessionFactory factory;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/app";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "";

    /**
     * @return фабрика сессий
     */
    private SessionFactory getFactory() {
        if (factory == null)
            factory = initializeFactory();
        return factory;
    }

    /**
     * Получить новую сессию
     */
    public Session getSession() {
        return getFactory().openSession();
    }

    /**
     * Инициализация соединения
     */
    private SessionFactory initializeFactory() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "none");
        properties.setProperty(AvailableSettings.AUTOCOMMIT, "true");
        properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "false");
        properties.setProperty(AvailableSettings.ISOLATION, String.valueOf(Connection.TRANSACTION_READ_COMMITTED));
        properties.setProperty(AvailableSettings.POOL_SIZE, "50");
        properties.setProperty(AvailableSettings.SHOW_SQL, "false");

        properties.setProperty(AvailableSettings.GLOBALLY_QUOTED_IDENTIFIERS, "true");
        properties.setProperty(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        properties.setProperty(AvailableSettings.JAKARTA_JDBC_USER, USERNAME);
        properties.setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, PASSWORD);
        properties.setProperty(AvailableSettings.JAKARTA_JDBC_URL, DB_URL);

        Configuration cfg = new Configuration();
        cfg.setProperties(properties);

        cfg.addAnnotatedClass(Writer.class);
        cfg.addAnnotatedClass(Post.class);
        cfg.addAnnotatedClass(Label.class);

        SessionFactory sessionFactory;
        try {
            sessionFactory = cfg.buildSessionFactory();
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка соединения с БД", e);
        }

        return sessionFactory;
    }
}
