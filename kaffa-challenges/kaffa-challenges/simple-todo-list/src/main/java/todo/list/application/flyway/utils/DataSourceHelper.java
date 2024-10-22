package todo.list.application.flyway.utils;

import javax.sql.DataSource;

public abstract class DataSourceHelper {
    public abstract DataSource createDataSource();
}
