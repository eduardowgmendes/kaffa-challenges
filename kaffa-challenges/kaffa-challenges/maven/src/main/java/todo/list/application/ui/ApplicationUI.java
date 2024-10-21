package todo.list.application.ui;

import jakarta.validation.constraints.PastOrPresent;
import todo.list.application.SimpleTodoListApplication;
import todo.list.application.domain.Task;
import todo.list.application.ui.models.TaskTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static todo.list.application.utils.ApplicationUtils.APPLICATION_NAME;
import static todo.list.application.utils.ApplicationUtils.APPLICATION_VERSION_CODE;

public class ApplicationUI implements ActionListener {

    private final SimpleTodoListApplication application;

    private JFrame mainJFramePanel;
    private JTextField taskTitleTextField;
    private JTextArea taskDescriptionTextArea;
    private JButton saveTaskButton;
    private JTable tasksHistoryTable;
    private JScrollPane scrollPane;
    private JPopupMenu popupMenu;

    private List<Task> all;

    private TaskTableModel tableModel;

    public ApplicationUI(SimpleTodoListApplication application) {
        this.application = application;
        this.initialize();
    }

    public void initialize() {
        taskTitleTextField = new JTextField("Title");
        taskDescriptionTextArea = new JTextArea("Description");

        saveTaskButton = new JButton("Save Task");
        saveTaskButton.addActionListener(this);

        mainJFramePanel = new JFrame(String.format("%s %s", APPLICATION_NAME, APPLICATION_VERSION_CODE));
        mainJFramePanel.setVisible(true);
        mainJFramePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFramePanel.setResizable(false);
        mainJFramePanel.setSize(480, 320);

        JPanel dataInputPanel = createDataInputPanel(taskTitleTextField, taskDescriptionTextArea, saveTaskButton);
        mainJFramePanel.add(dataInputPanel, BorderLayout.NORTH);

        this.all = application.all();

        if (all != null) {
            popupMenu = new JPopupMenu();

            JMenuItem edit = new JMenuItem("Edit");
            JMenuItem delete = new JMenuItem("Delete");
            JMenuItem markAsDone = new JMenuItem("Mark as Done");
            JMenuItem markAsCompleted = new JMenuItem("Mark as Completed");
            JMenuItem markAsErased = new JMenuItem("Mark as Erased");

            popupMenu.add(edit);
            popupMenu.add(delete);
            popupMenu.add(markAsDone);
            popupMenu.add(markAsCompleted);
            popupMenu.add(markAsErased);

            tasksHistoryTable = loadTable(all);
            JPanel tablePanel = createTablePanel(tasksHistoryTable);
            mainJFramePanel.add(new JScrollPane(tablePanel));

            tasksHistoryTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int row = tasksHistoryTable.rowAtPoint(e.getPoint());
                        int column = tasksHistoryTable.columnAtPoint(e.getPoint());

                        if (row != -1 && column != -1) {
                            tasksHistoryTable.setRowSelectionInterval(row, row);
                            popupMenu.show(tasksHistoryTable, e.getX(), e.getY());
                        }
                    }
                }
            });

            tasksHistoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int selectedRow = tasksHistoryTable.getSelectedRow();
                    if (!e.getValueIsAdjusting()) {
                        if (selectedRow != -1) {
                            Task selectedTask = tableModel.getTaskAt(selectedRow);
                            System.out.println(selectedTask);
                        }
                    }
                }
            });

            delete.addActionListener(e -> {
                int selectedRow = tasksHistoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    int result = JOptionPane.showConfirmDialog(mainJFramePanel, "Are you sure? This action cannot be undone.");
                    if (result == JOptionPane.OK_OPTION) {
                        Task taskAt = tableModel.getTaskAt(selectedRow);
                        application.deleteById(taskAt.getId());
                        tableModel.deleteTaskAt(selectedRow);
                    }
                }
            });

            edit.addActionListener(e -> {
                int selectedRow = tasksHistoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    JOptionPane.showInputDialog(mainJFramePanel, "Type the new information");
                }
            });

            markAsDone.addActionListener(e -> {
                int selectedRow = tasksHistoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    int result = JOptionPane.showConfirmDialog(mainJFramePanel, "Mark as Done?");
                    if (result == JOptionPane.OK_OPTION) {
                        Task taskAt = tableModel.getTaskAt(selectedRow);
                        application.markAsDone(taskAt.getId());
                        taskAt.setDone(true);
                    }
                }
            });

            markAsCompleted.addActionListener(e -> {
                int selectedRow = tasksHistoryTable.getSelectedRow();
                int result = JOptionPane.showConfirmDialog(mainJFramePanel, "Mark as Completed?");
                if (result == JOptionPane.OK_OPTION) {
                    application.markAsCompleted(all.get(selectedRow).getId());
                }
            });

            markAsErased.addActionListener(e -> {
                int selectedRow = tasksHistoryTable.getSelectedRow();
                int result = JOptionPane.showConfirmDialog(mainJFramePanel, "Mark as Erased?");
                if (result == JOptionPane.OK_OPTION) {
                    application.markAsErased(all.get(selectedRow).getId());
                }
            });
        } else {
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridBagLayout());
            infoPanel.add(new JLabel("No tasks were found!"));
            mainJFramePanel.add(infoPanel, BorderLayout.CENTER);
        }
    }

    private JPanel createTablePanel(JTable tasksHistoryTable) {
        JPanel tablePanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(tasksHistoryTable);

        tablePanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        tablePanel.add(scrollPane, gridBagConstraints);

        return tablePanel;
    }

    private JPanel createDataInputPanel(JTextField taskTitleTextField, JTextArea taskDescriptionTextArea, JButton saveTaskButton) {
        JPanel dataInputPanel = new JPanel();
        dataInputPanel.setLayout(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.BOTH;

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.weightx = 1.0;
        dataInputPanel.add(taskTitleTextField, layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.weightx = 1.0;
        layoutConstraints.weighty = 1.0;
        dataInputPanel.add(taskDescriptionTextArea, layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.weightx = 1.0;
        dataInputPanel.add(saveTaskButton, layoutConstraints);

        return dataInputPanel;
    }

    private JTable loadTable(List<Task> tasks) {
        String[] columns = createData("#", "Title", "Description", "Status", "Created At", "Updated At", "Erased At");
        this.tableModel = new TaskTableModel(tasks, columns);
        return new JTable(tableModel);
    }

    private String emptyIfNull(String value) {
        return value != null ? value : "";
    }

    private String emptyIfUnknownDate(LocalDateTime timestamp) {
        return timestamp != null ? timestamp.toString() : "";
    }

    private String[] createData(String... columns) {
        return columns;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Task task = new Task();
        String taskTitle = taskTitleTextField.getText();
        String taskDescription = taskDescriptionTextArea.getText();

        if (!taskTitle.isEmpty())
            task.setTitle(taskTitle);

        if (!taskDescription.isEmpty())
            task.setDescription(taskDescription);

        Task taskCreated = application.create(task);
        tableModel.addTask(taskCreated);
    }
}
