package todo.list.application;

import todo.list.application.database.repository.SimpleTaskRepository;
import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.domain.Task;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleTodoListApplication implements BasicPersistentDataApplication<Task> {

    private final SimpleTaskRepository simpleTaskRepository;

    public SimpleTodoListApplication(SimpleTaskRepository simpleTaskRepository) {
        this.simpleTaskRepository = simpleTaskRepository;
    }

    @Override
    public List<Task> all() {
        List<TaskEntity> allTasksFound = simpleTaskRepository.findAll();

        if (allTasksFound.isEmpty()) return null;

        return allTasksFound.stream()
                .map(Task::create)
                .collect(Collectors.toList());
    }

    @Override
    public Task create(Task task) {
        Objects.requireNonNull(task, "task cannot be null");
        return Task.create(simpleTaskRepository.saveOrUpdate(TaskEntity.create(task)));
    }

    @Override
    public Task update(Task task) {
        Objects.requireNonNull(task, "task cannot be null");
        return Task.create(simpleTaskRepository.saveOrUpdate(TaskEntity.create(task)));
    }

    @Override
    public void delete(Task task) {
        Objects.requireNonNull(task, "task cannot be null");
        simpleTaskRepository.deleteById(task.getId());
    }

    @Override
    public void deleteThese(List<Task> tasks) {
        Objects.requireNonNull(tasks, "task list cannot be null");
        tasks.forEach(this::delete);
    }

    @Override
    public void clear() {
        simpleTaskRepository.clear();
    }

    public void markAsDone(long taskId) {
        simpleTaskRepository.markAsDone(taskId);
    }

    public void markAsCompleted(long taskId) {
        simpleTaskRepository.markAsCompleted(taskId);
    }

    public void markAsErased(long taskId) {
        simpleTaskRepository.eraseById(taskId);
    }

}
