package databuilder;

import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class TaskBuilder {

    private final TaskEntity task = new TaskEntity();

    public TaskBuilder withDefault() {
        task.setTitle("Task Test");
        task.setDescription("Task created to test purposes only.");
        task.setStatus(Status.RUNNING);
        task.setDone(false);
        task.setCompleted(false);
        task.setErased(false);
        task.setCreatedAt(LocalDateTime.now());
        return this;
    }

    public TaskBuilder withId(long id) {
        task.setId(id);
        return this;
    }

    public TaskBuilder withTitle(String title) {
        task.setTitle(title);
        return this;
    }

    public TaskBuilder withDescription(String description) {
        task.setDescription(description);
        return this;
    }

    public TaskBuilder withStatus(Status status) {
        task.setStatus(status);
        return this;
    }

    public TaskBuilder isDone(boolean isDone) {
        task.setDone(isDone);
        task.setDoneAt(LocalDateTime.now());
        return this;
    }

    public TaskBuilder isCompleted(boolean isCompleted) {
        task.setCompleted(isCompleted);
        task.setCompletedAt(LocalDateTime.now());
        return this;
    }

    public TaskBuilder isErased(boolean isErased) {
        task.setErased(isErased);
        task.setErasedAt(LocalDateTime.now());
        return this;
    }

    public TaskBuilder withTags(List<String> tags) {
        task.setTags(tags);
        return this;
    }

    public TaskEntity build() {
        return task;
    }
}
