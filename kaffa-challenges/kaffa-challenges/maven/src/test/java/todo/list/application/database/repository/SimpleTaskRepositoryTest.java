package todo.list.application.database.repository;

import databuilder.TaskBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import todo.list.application.database.configuration.H2DatabaseConfig;
import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTaskRepositoryTest {

    private final EntityManager entityManager = H2DatabaseConfig.getEntityManagerFactory().createEntityManager();
    private final SimpleTaskRepository repository = new SimpleTaskRepository(entityManager);

    @BeforeEach
    public void setup() {
        this.persistTestData();
    }

    @Test
    public void shouldReturnAllTasks_whenRequested() {
        List<TaskEntity> allTasksFound = repository.findAll();

        assertThat(allTasksFound).isNotNull();
        assertThat(allTasksFound).isNotEmpty();
    }

    @Test
    public void shouldReturnSpecifiedTask_whenTaskIdProvided() {

        TaskEntity task = new TaskBuilder()
                .withDefault()
                .build();

        task = repository.save(task);

        Optional<TaskEntity> optionalTask = repository.findById(task.getId());

        assertThat(optionalTask).isNotEmpty();
        TaskEntity taskEntity = optionalTask.get();
        assertThat(taskEntity.getId()).isEqualTo(task.getId());
    }

    @Test
    public void shouldCreateNewTask_whenValidDataProvided() {
        TaskEntity task = new TaskBuilder()
                .withDefault()
                .build();

        TaskEntity taskSaved = repository.save(task);

        assertThat(taskSaved).isNotNull();
        assertThat(taskSaved.getId()).isEqualTo(task.getId());
        assertThat(taskSaved.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskSaved.getDescription()).isEqualTo(task.getDescription());
        assertThat(taskSaved.getStatus()).isEqualTo(task.getStatus());
        assertThat(taskSaved.isDone()).isEqualTo(task.isDone());
        assertThat(taskSaved.isCompleted()).isEqualTo(task.isCompleted());
        assertThat(taskSaved.isErased()).isEqualTo(task.isErased());
        assertThat(taskSaved.getTags()).isEmpty();
        assertThat(taskSaved.getCreatedAt()).isEqualTo(task.getCreatedAt());
        assertThat(taskSaved.getUpdatedAt()).isEqualTo(task.getUpdatedAt());
        assertThat(taskSaved.getErasedAt()).isEqualTo(task.getErasedAt());
    }

    @Test
    public void shouldUpdateTask_whenTaskIdExists() {
        TaskEntity task = new TaskBuilder()
                .withDefault()
                .build();

        task = repository.save(task);
        task.setTitle("New task title");
        task.setDescription("New task description");
        task.setStatus(Status.COMPLETED);
        task.setDone(true);

        TaskEntity taskUpdated = repository.update(task);

        assertThat(taskUpdated).isNotNull();
        assertThat(taskUpdated.getId()).isEqualTo(task.getId());
        assertThat(taskUpdated.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskUpdated.getDescription()).isEqualTo(task.getDescription());
        assertThat(taskUpdated.getStatus()).isEqualTo(task.getStatus());
        assertThat(taskUpdated.isDone()).isEqualTo(task.isDone());
        assertThat(taskUpdated.isCompleted()).isEqualTo(task.isCompleted());
        assertThat(taskUpdated.isErased()).isEqualTo(task.isErased());
        assertThat(taskUpdated.getTags()).isEmpty();
        assertThat(taskUpdated.getCreatedAt()).isEqualTo(task.getCreatedAt());
        assertThat(taskUpdated.getUpdatedAt()).isEqualTo(task.getUpdatedAt());
        assertThat(taskUpdated.getErasedAt()).isEqualTo(task.getErasedAt());
    }

    @Test
    public void shouldDeleteTask_whenTaskIdExists() {
        TaskEntity task = new TaskBuilder()
                .withDefault()
                .build();

        task = repository.save(task);

        repository.deleteById(task.getId());

        Optional<TaskEntity> optionalTask = repository.findById(task.getId());

        assertThat(optionalTask).isEmpty();
    }

    @Test
    public void shouldMarkAsErased_whenTaskIdExists() {
        repository.eraseById(1);

        Optional<TaskEntity> optionalTask = repository.findById(1);

        assertThat(optionalTask).isNotEmpty();
        TaskEntity taskEntity = optionalTask.get();
        assertThat(taskEntity.isErased()).isTrue();
    }

    @Test
    public void shouldMarkTaskAsDone_whenValidTaskId() {
        TaskEntity task = new TaskBuilder()
                .withDefault()
                .build();

        task = repository.save(task);

        repository.markAsDone(task.getId());

        Optional<TaskEntity> optionalTask = repository.findById(task.getId());

        assertThat(optionalTask).isNotEmpty();
        TaskEntity taskEntity = optionalTask.get();
        assertThat(taskEntity.isDone()).isTrue();
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenMarkAsDoneWithInvalidTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.markAsDone(64));
    }

    @Test
    public void shouldMarkTaskAsCompleted_whenValidTaskId() {
        repository.markAsCompleted(1);

        Optional<TaskEntity> optionalTask = repository.findById(1);

        assertThat(optionalTask).isNotEmpty();
        TaskEntity taskEntity = optionalTask.get();
        assertThat(taskEntity.isCompleted()).isTrue();
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenMarkAsCompletedWithInvalidTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.markAsCompleted(64));
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenTryToSaveNullData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenTryToUpdateNullData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.update(null));
    }

    @Test
    public void shouldReturnListOfTasks_whenValidDescriptionProvided() {
        List<TaskEntity> tasksFound = repository.findByDescription("vacuum the carpet");

        assertThat(tasksFound).isNotEmpty();
    }

    @Test
    public void shouldClearAllData_whenClearInvoked() {
        repository.clear();

        List<TaskEntity> tasksFound = repository.findAll();

        assertThat(tasksFound).isEmpty();
    }

    private void persistTestData() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(new TaskBuilder()
                .withDefault()
                .withTitle("Clean the Living Room")
                .withDescription("Dust the shelves, vacuum the carpet, and organize the clutter in the living room.")
                .build());

        transaction.commit();
    }
}
