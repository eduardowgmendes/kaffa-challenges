package todo.list.application.domain;

import org.modelmapper.ModelMapper;
import todo.list.application.database.shared.entity.TaskEntity;
import todo.list.application.database.shared.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task {

    private long id;
    private String title;
    private String description;
    private Status status;
    private boolean done;
    private boolean completed;
    private boolean erased;
    private boolean running;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime updatedAt;
    private LocalDateTime erasedAt;
    private LocalDateTime createdAt;
    private LocalDateTime doneAt;
    private LocalDateTime completedAt;

    public static Task from(TaskEntity taskEntity) {
        if (taskEntity == null) return null;
        return new ModelMapper().map(taskEntity, Task.class);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isErased() {
        return erased;
    }

    public void setErased(boolean erased) {
        this.erased = erased;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getErasedAt() {
        return erasedAt;
    }

    public void setErasedAt(LocalDateTime erasedAt) {
        this.erasedAt = erasedAt;
    }

    public LocalDateTime getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(LocalDateTime doneAt) {
        this.doneAt = doneAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && done == task.done && completed == task.completed && erased == task.erased && running == task.running && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status && Objects.equals(tags, task.tags) && Objects.equals(updatedAt, task.updatedAt) && Objects.equals(erasedAt, task.erasedAt) && Objects.equals(createdAt, task.createdAt) && Objects.equals(doneAt, task.doneAt) && Objects.equals(completedAt, task.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, done, completed, erased, running, tags, updatedAt, erasedAt, createdAt, doneAt, completedAt);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", done=" + done +
                ", completed=" + completed +
                ", erased=" + erased +
                ", running=" + running +
                ", tags=" + tags +
                ", updatedAt=" + updatedAt +
                ", erasedAt=" + erasedAt +
                ", createdAt=" + createdAt +
                ", doneAt=" + doneAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
