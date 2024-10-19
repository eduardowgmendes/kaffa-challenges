package todo.list.application.database.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.modelmapper.ModelMapper;
import todo.list.application.database.converter.LocalDateTimeConverter;
import todo.list.application.database.converter.StatusConverter;
import todo.list.application.domain.Task;
import todo.list.application.database.shared.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "title cannot be null")
    @Size(min = 1, max = 128, message = "title must be between 1 and 128 characters")
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @Size(max = 50000, message = "description cannot exceed 50000 characters")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "status cannot be null")
    @Convert(converter = StatusConverter.class)
    @Column(name = "status", columnDefinition = "TEXT", nullable = false)
    private Status status;

    @Column(name = "is_done", nullable = false)
    private boolean done;

    @Column(name = "is_completed", nullable = false)
    private boolean completed;

    @Column(name = "is_erased", nullable = false)
    private boolean erased;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "taskId"))
    @Column(name = "tag", nullable = false)
    private List<String> tags = new ArrayList<>();

    @PastOrPresent(message = "creation date cannot be in the future")
    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @PastOrPresent(message = "update date cannot be in the future")
    @Column(name = "updated_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updatedAt;

    @PastOrPresent(message = "erase date cannot be in the future")
    @Column(name = "erased_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime erasedAt;

    @PastOrPresent(message = "done date cannot be in the future")
    @Column(name = "done_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime doneAt;

    @PastOrPresent(message = "completion date cannot be in the future")
    @Column(name = "completed_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime completedAt;

    public static TaskEntity create(Task task) {
        return new ModelMapper().map(task, TaskEntity.class);
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

    public boolean isRunning() {
        return status.isRunning();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isCompleted() {
        return completed;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return id == that.id && done == that.done && completed == that.completed && erased == that.erased && Objects.equals(title, that.title) && Objects.equals(description, that.description) && status == that.status && Objects.equals(tags, that.tags) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(erasedAt, that.erasedAt) && Objects.equals(doneAt, that.doneAt) && Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, done, completed, erased, tags, createdAt, updatedAt, erasedAt, doneAt, completedAt);
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", done=" + done +
                ", completed=" + completed +
                ", erased=" + erased +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", erasedAt=" + erasedAt +
                ", doneAt=" + doneAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
