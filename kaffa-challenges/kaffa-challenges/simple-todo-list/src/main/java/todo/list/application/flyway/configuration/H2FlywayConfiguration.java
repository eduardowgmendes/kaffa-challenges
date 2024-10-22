package todo.list.application.flyway.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.list.application.flyway.utils.DataSourceHelper;

import javax.sql.DataSource;

public class H2FlywayConfiguration extends DataSourceHelper implements DatabaseConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger("H2FlywayConfiguration");

    @Override
    public void migrate() {
        DataSource dataSource = createDataSource();

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource).locations("classpath:db/migration")
                .load();

        for (Location location : flyway.getConfiguration().getLocations()) {
            LOGGER.info(location.getDescriptor());
        }

        LOGGER.info("Starting migration process. This may take some time :(");
        flyway.migrate();
        LOGGER.info("All migrations executed successfully :)");
    }

    @Override
    public DataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:./src/test/resources/h2/simple_todo_list;AUTO_SERVER=TRUE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDefaultSchema("PUBLIC");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        return dataSource;
    }
}
