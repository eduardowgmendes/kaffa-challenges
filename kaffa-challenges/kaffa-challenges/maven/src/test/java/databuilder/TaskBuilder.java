package databuilder;

import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskBuilder {

    private long id;
    private String title;
    private String description;
    private Status status;
    private boolean done;
    private boolean completed;
    private boolean erased;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime erasedAt;
    private LocalDateTime doneAt;
    private LocalDateTime completedAt;

    public TaskBuilder id(long id) {
        this.id = id;
        return this;
    }

    public TaskBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public TaskBuilder done(boolean done) {
        this.done = done;
        return this;
    }

    public TaskBuilder completed(boolean completed) {
        this.completed = completed;
        return this;
    }

    public TaskBuilder erased(boolean erased) {
        this.erased = erased;
        return this;
    }

    public TaskBuilder tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public TaskBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TaskBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public TaskBuilder erasedAt(LocalDateTime erasedAt) {
        if (erased)
            this.erasedAt = erasedAt;
        return this;
    }

    public TaskBuilder doneAt(LocalDateTime doneAt) {
        if (done)
            this.doneAt = doneAt;
        return this;
    }

    public TaskBuilder completedAt(LocalDateTime completedAt) {
        if (completed)
            this.completedAt = completedAt;
        return this;
    }

    public TaskEntity build() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setTitle(title);
        taskEntity.setDescription(description);
        taskEntity.setStatus(status);
        taskEntity.setDone(done);
        taskEntity.setCompleted(completed);
        taskEntity.setErased(erased);
        taskEntity.setTags(tags);
        taskEntity.setCreatedAt(createdAt);
        taskEntity.setUpdatedAt(updatedAt);
        taskEntity.setErasedAt(erasedAt);
        taskEntity.setDoneAt(doneAt);
        taskEntity.setCompletedAt(completedAt);
        return taskEntity;
    }
}
