package todo.list.application.ui.models;

import todo.list.application.domain.Task;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

    private List<Task> tasks;
    private String[] columns;

    public TaskTableModel(List<Task> tasks, String[] columns) {
        this.tasks = tasks;
        this.columns = columns;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String[] getColumns() {
        return columns;
    }

    @Override
    public int getRowCount() {
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columns != null ? columns.length : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> task.getId();
            case 1 -> task.getTitle();
            case 2 -> task.getDescription();
            case 3 -> task.getStatus().name();
            case 4 -> task.getCreatedAt();
            case 5 -> task.getUpdatedAt();
            case 6 -> task.getErasedAt();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void addTask(Task task) {
        tasks.add(task);
        fireTableRowsInserted(tasks.size() - 1, tasks.size() - 1);
    }

    public void deleteTaskAt(int rowIndex) {
        tasks.remove(rowIndex);
        fireTableRowsDeleted(tasks.size() - 1, tasks.size() - 1);
    }

    public void updateTaskAt(int rowIndex, Task task) {
        tasks.set(rowIndex, task);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public Task getTaskAt(int rowIndex) {
        return tasks.get(rowIndex);
    }
}
