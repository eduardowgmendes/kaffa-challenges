package todo.list.application.ui;

import org.apache.commons.lang3.StringUtils;
import todo.list.application.SimpleTodoListApplication;
import todo.list.application.database.shared.enums.Status;
import todo.list.application.domain.Task;
import todo.list.application.ui.models.TaskTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;

import static todo.list.application.utils.ApplicationUtils.APPLICATION_NAME;
import static todo.list.application.utils.ApplicationUtils.APPLICATION_VERSION_CODE;

public class ApplicationUI implements ActionListener {

    private final SimpleTodoListApplication application;
    private JFrame mainJFramePanel;
    private JTextField taskTitleTextField;
    private JTextArea taskDescriptionTextArea;
    private JButton saveTaskButton;
    private JTable tasksHistoryTable;
    private JPopupMenu popupMenu;

    private JTextField optionPaneTaskTitleTextField = new JTextField(10);
    private JTextArea optionPaneTaskTitleTextArea = new JTextArea(3, 10);
    private JComboBox<Status> taskStatusComboBox = new JComboBox<>(Status.values());

    private List<Task> allTasks;
    private TaskTableModel tableModel;

    private String[] columns = {"#", "Title", "Description", "Status", "Created At", "Updated At", "Erased At"};

    public ApplicationUI(SimpleTodoListApplication application) {
        this.application = application;
        this.initialize();
    }

    public void initialize() {
        setupMainFrame();
        setupDataInputPanel();
        loadTaskHistoryTable();
    }

    private void setupMainFrame() {
        mainJFramePanel = new JFrame(String.format("%s %s", APPLICATION_NAME, APPLICATION_VERSION_CODE));
        mainJFramePanel.setVisible(true);
        mainJFramePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFramePanel.setResizable(false);
        mainJFramePanel.setSize(480, 320);
    }

    private void setupDataInputPanel() {
        taskTitleTextField = new JTextField("Title");
        taskDescriptionTextArea = new JTextArea("Description");

        saveTaskButton = new JButton("Save Task");
        saveTaskButton.addActionListener(this);

        JPanel dataInputPanel = createDataInputPanel(taskTitleTextField, taskDescriptionTextArea, saveTaskButton);
        mainJFramePanel.add(dataInputPanel, BorderLayout.NORTH);
    }

    private void loadTaskHistoryTable() {
        this.allTasks = application.all();
        if (allTasks != null) {
            setupPopupMenu();
            tasksHistoryTable = loadTable(columns, allTasks);
            JPanel tablePanel = createTablePanel(tasksHistoryTable);
            mainJFramePanel.add(new JScrollPane(tablePanel));
            addTableListeners();
        } else {
            displayNoTasksMessage();
        }
    }

    private void setupPopupMenu() {
        popupMenu = new JPopupMenu();
        addMenuItem(popupMenu, "Edit", this::editTask);
        addMenuItem(popupMenu, "Delete", this::deleteTask);
        addMenuItem(popupMenu, "Mark as Done", this::markTaskAsDone);
        addMenuItem(popupMenu, "Mark as Completed", this::markTaskAsCompleted);
        addMenuItem(popupMenu, "Mark as Erased", this::markTaskAsErased);
    }

    private void addMenuItem(JPopupMenu menu, String label, ActionListener action) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(action);
        menu.add(item);
    }

    private void addTableListeners() {
        tasksHistoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tasksHistoryTable.rowAtPoint(e.getPoint());
                    tasksHistoryTable.setRowSelectionInterval(row, row);
                    popupMenu.show(tasksHistoryTable, e.getX(), e.getY());
                }
            }
        });

        tasksHistoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tasksHistoryTable.getSelectedRow() != -1) {
                    Task selectedTask = tableModel.getTaskAt(tasksHistoryTable.getSelectedRow());
                    System.out.println(selectedTask);
                }
            }
        });
    }

    private void displayNoTasksMessage() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.add(new JLabel("No tasks were found!"));
        mainJFramePanel.add(infoPanel, BorderLayout.CENTER);
    }

    private JPanel createDataInputPanel(JTextField taskTitleTextField, JTextArea taskDescriptionTextArea, JButton saveTaskButton) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        addToPanel(panel, taskTitleTextField, c, 0, 0, 1.0);
        addToPanel(panel, taskDescriptionTextArea, c, 0, 1, 1.0);
        addToPanel(panel, saveTaskButton, c, 0, 2, 1.0);

        return panel;
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints c, int x, int y, double weightx) {
        c.gridx = x;
        c.gridy = y;
        c.weightx = weightx;
        c.weighty = 1.0;
        panel.add(component, c);
    }

    private JPanel createTablePanel(JTable table) {
        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;

        panel.add(scrollPane, c);
        return panel;
    }

    private JTable loadTable(String[] columns, List<Task> tasks) {
        tableModel = new TaskTableModel(tasks, columns);
        return new JTable(tableModel);
    }

    private void editTask(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow == -1) return;

        long taskTableModelId = tableModel.getTaskAt(selectedRow).getId();

        Task taskFound = application.findById(taskTableModelId);

        if (taskFound != null) {
            optionPaneTaskTitleTextField.setText(taskFound.getTitle());
            optionPaneTaskTitleTextArea.setText(taskFound.getDescription());
            taskStatusComboBox.setSelectedItem(taskFound.getStatus());
        }

        JPanel editPanel = createEditPanel();

        int result = JOptionPane.showConfirmDialog(mainJFramePanel, editPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (taskFound != null && validateTaskInput(optionPaneTaskTitleTextField, optionPaneTaskTitleTextArea)) {
                taskFound.setTitle(optionPaneTaskTitleTextField.getText());
                taskFound.setDescription(optionPaneTaskTitleTextArea.getText());
                taskFound.setStatus((Status) taskStatusComboBox.getSelectedItem());

                application.update(taskFound);
                tableModel.updateTaskAt(selectedRow, taskFound);
            }
        }
    }

    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        panel.add(new JLabel("Title:"), gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panel.add(optionPaneTaskTitleTextField, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        panel.add(new JLabel("Description:"), gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panel.add(new JScrollPane(optionPaneTaskTitleTextArea), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Status:"), gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        panel.add(taskStatusComboBox, gridBagConstraints);

        return panel;
    }

    private boolean validateTaskInput(JTextField taskTitleTextField, JTextArea taskDescriptionTextArea) {
        if (StringUtils.isBlank(taskTitleTextField.getText())) {
            JOptionPane.showMessageDialog(mainJFramePanel, "Task Title cannot be empty!");
            return false;
        }
        if (StringUtils.isBlank(taskDescriptionTextArea.getText())) {
            JOptionPane.showMessageDialog(mainJFramePanel, "Task Description cannot be empty!");
            return false;
        }
        return true;
    }

    private void deleteTask(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow == -1) return;

        int result = JOptionPane.showConfirmDialog(mainJFramePanel, "Are you sure? This action cannot be undone.");
        if (result == JOptionPane.OK_OPTION) {
            Task task = tableModel.getTaskAt(selectedRow);
            application.deleteById(task.getId());
            tableModel.deleteTaskAt(selectedRow);
        }
    }

    private void markTaskAsDone(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow != -1) {
            application.markAsDone(allTasks.get(selectedRow).getId());
            Task taskAsDone = tableModel.getTaskAt(selectedRow);
            taskAsDone.setDone(true);
            taskAsDone.setDoneAt(LocalDateTime.now());
            tableModel.updateTaskAt(selectedRow, taskAsDone);
        }
    }

    private void markTaskAsCompleted(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow != -1) {
            application.markAsCompleted(allTasks.get(selectedRow).getId());
            Task taskAsCompleted = tableModel.getTaskAt(selectedRow);
            taskAsCompleted.setStatus(Status.COMPLETED);
            tableModel.updateTaskAt(selectedRow, taskAsCompleted);
        }
    }

    private void markTaskAsErased(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow != -1) {
            application.markAsErased(allTasks.get(selectedRow).getId());
            Task taskErased = tableModel.getTaskAt(selectedRow);
            taskErased.setErased(true);
            taskErased.setErasedAt(LocalDateTime.now());
            tableModel.updateTaskAt(selectedRow, taskErased);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (validateTaskInput(taskTitleTextField, taskDescriptionTextArea)) {
            Task saveTask = new Task();
            saveTask.setTitle(taskTitleTextField.getText());
            saveTask.setDescription(taskDescriptionTextArea.getText());
            Task task = application.create(saveTask);
            tableModel.addTask(task);
            resetForm();
        }
    }

    private void resetForm() {
        taskTitleTextField.setText("");
        taskDescriptionTextArea.setText("");
    }
}
