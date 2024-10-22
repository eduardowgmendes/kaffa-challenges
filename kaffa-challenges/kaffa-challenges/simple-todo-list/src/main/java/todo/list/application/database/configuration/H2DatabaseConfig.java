package todo.list.application.database.configuration;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;

import java.util.HashMap;
import java.util.Map;

public class H2DatabaseConfig {

    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {

        if (entityManagerFactory == null) {
            Map<String, Object> settings = new HashMap<>();
            settings.put(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.h2.Driver");
            settings.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:mem:simple_todo_list;DB_CLOSE_DELAY=-1");
            settings.put(AvailableSettings.JAKARTA_JDBC_USER, "sa");
            settings.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, "");
            settings.put(AvailableSettings.HBM2DDL_AUTO, "create");
            settings.put(AvailableSettings.SHOW_SQL, "true");

            entityManagerFactory = Persistence.createEntityManagerFactory("TestProfilePersistenceUnit", settings);
        }

        return entityManagerFactory;
    }
}
