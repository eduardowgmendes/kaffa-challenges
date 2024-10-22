package todo.list.application;

import todo.list.application.database.configuration.DefaultConfiguration;
import todo.list.application.database.repository.SimpleTaskRepository;
import todo.list.application.flyway.configuration.DefaultFlywayConfiguration;
import todo.list.application.ui.ApplicationUI;

import javax.swing.*;

public class ApplicationExecutor {

    public static void main(String[] args) {

        new DefaultFlywayConfiguration()
                .migrate();

        SwingUtilities.invokeLater(() -> new ApplicationUI(new SimpleTodoListApplication(new SimpleTaskRepository(DefaultConfiguration.getEntityManager()))));
    }
}
