package todo.list.application.database.shared.enums;

public enum Status {

    RUNNING("Running"), DONE("Done"), COMPLETED("Completed");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRunning() {
        return this == RUNNING;
    }

    public boolean isDone() {
        return this == DONE;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    @Override
    public String toString() {
        return "Status{" +
                "description='" + description + '\'' +
                '}';
    }
}
