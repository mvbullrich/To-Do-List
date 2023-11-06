import GUI.TaskGUI;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Manager");
        TaskGUI taskGUI = new TaskGUI();

        frame.add(taskGUI);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}
