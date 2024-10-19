package todo.list.application.database.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAConfiguration {

    private static final EntityManagerFactory entityManagerFactory;

    static {
        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/simple-todo-list");
        props.put("jakarta.persistence.jdbc.user", "postgres");
        props.put("jakarta.persistence.jdbc.password", "postgres");
        props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.show_sql", "true");

        entityManagerFactory = Persistence.createEntityManagerFactory("SimpleTodoListPersistenceUnit", props);
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
