package todo.list.application;

import databuilder.TaskBuilder;
import org.junit.jupiter.api.Test;
import todo.list.application.database.repository.SimpleTaskRepository;
import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;
import todo.list.application.domain.Task;

import java.util.List;

public class SimpleTodoListApplicationTest {

    private final SimpleTodoListApplication application = new SimpleTodoListApplication(new SimpleTaskRepository());

    private TaskEntity provideTask() {
        return new TaskBuilder()
                .id(1)
                .title("Buy Tomatoes on SuperMarket")
                .description("I need to head to the nearest supermarket to pick up some fresh tomatoes for making a delicious macaroni dish")
                .status(Status.RUNNING)
                .tags(List.of("macaroni", "tomatoes", "food", "cook"))
                .build();

        // TODO - View a way to use h2 as test database and ensure on database the data for testing, add flyway migrations, and configure Docker to run with Postgresql
    }

    @Test
    public void createTaskSuccessfully() {
        application.create(Task.create(provideTask()));
    }

    @Test
    public void updateTaskByIdSuccessfully() {
        application.update(Task.create(provideTask()));
    }

    @Test
    public void deleteTaskByIdSuccessfully() {
        application.delete(Task.create(provideTask()));
    }

    @Test
    public void eraseTaskByIdSuccessfully() {
        application.markAsErased(1);
    }

    @Test
    public void markTaskAsDoneSuccessfully() {
        application.markAsDone(1);
    }

    @Test
    public void markTaskAsCompletedSuccessfully() {
        application.markAsCompleted(1);
    }
}
