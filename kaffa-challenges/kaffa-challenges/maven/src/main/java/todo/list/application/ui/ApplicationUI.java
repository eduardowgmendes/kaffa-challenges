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
    private JTextArea optionPaneTaskTitleTextArea = new JTextArea(5, 10);
    private JComboBox<Status> taskStatusComboBox = new JComboBox<>(Status.values());

    private List<Task> all;
    private TaskTableModel tableModel;

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
        this.all = application.all();
        if (all != null) {
            setupPopupMenu();
            tasksHistoryTable = loadTable(all);
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

    private JTable loadTable(List<Task> tasks) {
        String[] columns = {"#", "Title", "Description", "Status", "Created At", "Updated At", "Erased At"};
        tableModel = new TaskTableModel(tasks, columns);
        return new JTable(tableModel);
    }

    private void editTask(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow == -1) return;

        Task taskToUpdate = tableModel.getTaskAt(selectedRow);

        optionPaneTaskTitleTextField.setText(taskToUpdate.getTitle());
        optionPaneTaskTitleTextArea.setText(taskToUpdate.getDescription());
        taskStatusComboBox.setSelectedItem(taskToUpdate.getStatus());

        JPanel editPanel = createEditPanel();

        int result = JOptionPane.showConfirmDialog(mainJFramePanel, editPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (validateTaskInput(optionPaneTaskTitleTextField, optionPaneTaskTitleTextArea)) {
                taskToUpdate.setTitle(optionPaneTaskTitleTextField.getText());
                taskToUpdate.setDescription(optionPaneTaskTitleTextArea.getText());
                taskToUpdate.setStatus((Status) taskStatusComboBox.getSelectedItem());

                application.update(taskToUpdate);
                tableModel.updateTaskAt(selectedRow, taskToUpdate);
            }
        }
    }

    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Title:"));
        panel.add(optionPaneTaskTitleTextField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(optionPaneTaskTitleTextArea));
        panel.add(new JLabel("Status:"));
        panel.add(taskStatusComboBox);
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
            application.markAsDone(all.get(selectedRow).getId());
            tableModel.getTaskAt(selectedRow).setDone(true);
        }
    }

    private void markTaskAsCompleted(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow != -1) {
            application.markAsCompleted(all.get(selectedRow).getId());
            tableModel.getTaskAt(selectedRow).setStatus(Status.COMPLETED);
        }
    }

    private void markTaskAsErased(ActionEvent e) {
        int selectedRow = tasksHistoryTable.getSelectedRow();
        if (selectedRow != -1) {
            application.markAsErased(all.get(selectedRow).getId());
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
