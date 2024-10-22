package todo.list.application.flyway.configuration;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.list.application.flyway.utils.DataSourceHelper;

import javax.sql.DataSource;

public class DefaultFlywayConfiguration extends DataSourceHelper implements DatabaseConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger("FlywayConfiguration");

    @Override
    public void migrate() {
        DataSource dataSource = createDataSource();

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource).locations("classpath:db/migration")
                .load();

        LOGGER.info("Starting migration process. This may take some time :(");
        flyway.migrate();
        LOGGER.info("All migrations executed successfully :)");
    }

    @Override
    public DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/simple-todo-list");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }
}
