package todo.list.application.database.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfiguration {

    private static final EntityManagerFactory entityManagerFactory;

    static {
        Map<String, String> settings = new HashMap<>();
        settings.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/simple-todo-list");
        settings.put("jakarta.persistence.jdbc.user", "postgres");
        settings.put("jakarta.persistence.jdbc.password", "postgres");
        settings.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        settings.put("hibernate.hbm2ddl.auto", "none");
        settings.put("hibernate.show_sql", "true");

        entityManagerFactory = Persistence.createEntityManagerFactory("ProductionProfilePersistenceUnit", settings);
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
