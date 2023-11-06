package GUI;

import Controler.Task;
import Service.ServiceException;
import Service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class TaskGUI extends JPanel {
    private TaskService taskService;
    private JPanel panelTask;
    private JPanel jPanelButtons;
    private JTextField jTextFieldTitle;
    private JTextField jTextFieldDescription;
    private JTable jTable;
    private DefaultTableModel tableModel;

    public TaskGUI() {
        taskService = new TaskService();
        createUI();
    }

    public void createUI() {
        panelTask = new JPanel();
        jPanelButtons = new JPanel();
        panelTask.setLayout(new GridLayout(2, 1));

        JLabel jLabelTitle = new JLabel("Title:");
        jTextFieldTitle = new JTextField(50);
        JLabel jLabelDescription = new JLabel("Description:");
        jTextFieldDescription = new JTextField(50);

        panelTask.add(jLabelTitle);
        panelTask.add(jTextFieldTitle);
        panelTask.add(jLabelDescription);
        panelTask.add(jTextFieldDescription);

        setLayout(new BorderLayout());
        add(panelTask, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableModel.addColumn("Description");
        tableModel.addColumn("Finished");
        jTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(jTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton jButtonAdd = new JButton("Add Task");
        JButton jButtonModify = new JButton("Modify Task");
        JButton jButtonDelete = new JButton("Delete Task");

        jPanelButtons.add(jButtonAdd);
        jPanelButtons.add(jButtonModify);
        jPanelButtons.add(jButtonDelete);

        add(jPanelButtons, BorderLayout.SOUTH);

        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task task = new Task();
                try {
                    if (jTextFieldTitle.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "The task must have a title");
                    } else {
                        task.setTitle(jTextFieldTitle.getText());
                        task.setDescription(jTextFieldDescription.getText());
                        task.setFinished(false);

                        taskService.save(task);
                        JOptionPane.showMessageDialog(null, "Task saved successfully :D");

                        updateTable();
                    }
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error. Could not save task.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        jButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Task> tasks = taskService.getAllTasks();

                    int selectedRow = jTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int modelRow = jTable.convertRowIndexToModel(selectedRow);
                        Task selectedTask = tasks.get(modelRow);

                        try {
                            taskService.delete(selectedTask.getId());
                            JOptionPane.showMessageDialog(null, "Task deleted successfully :)");
                            updateTable();
                        } catch (ServiceException serEx) {
                            JOptionPane.showMessageDialog(null, "Error. Could not delete task.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a task to delete.");
                    }
                } catch (ServiceException ex){
                    JOptionPane.showMessageDialog(null, "Error " + ex.getMessage());
                }
            }
        });

        jButtonModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Task> tasks = taskService.getAllTasks();
                    int selectedRow = jTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int modelRow = jTable.convertRowIndexToModel(selectedRow);
                        Task selectedTask = tasks.get(modelRow);
                        String newTitle = JOptionPane.showInputDialog("New Title:", selectedTask.getTitle());
                        String newDescription = JOptionPane.showInputDialog("New Description:", selectedTask.getDescription());
                        int option = JOptionPane.showConfirmDialog(null, "Is the task finished?", "Task Status", JOptionPane.YES_NO_OPTION);
                        boolean newFinished;
                        if (option == JOptionPane.YES_OPTION) {
                            newFinished = true;
                        } else {
                            newFinished = false;
                        }
                        if (newTitle != null && newDescription != null) {
                            selectedTask.setTitle(newTitle);
                            selectedTask.setDescription(newDescription);
                            selectedTask.setFinished(newFinished);
                            try {
                                taskService.modify(selectedTask); // Modificar la tarea en la base de datos
                                JOptionPane.showMessageDialog(null, "Task modified successfully :)");
                                updateTable(); // Actualizar la tabla
                            } catch (ServiceException serEx) {
                                JOptionPane.showMessageDialog(null, "Error. Could not modify task.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a task to modify.");
                    }
                } catch (ServiceException ex){
                    JOptionPane.showMessageDialog(null, "Error " + ex.getMessage());
                }
            }
        });

        updateTable();
    }

    private void updateTable() {
        try {
            tableModel.setRowCount(0);
            ArrayList<Task> tasks = taskService.getAllTasks();

            for (Task task : tasks) {
                tableModel.addRow(new Object[]{task.getTitle(), task.getDescription(), task.getStatus()});
            }
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error when getting task list");
        }
    }
}
