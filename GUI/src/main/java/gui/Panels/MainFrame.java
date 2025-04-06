package gui.Panels;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    JFrame frame;
    JTabbedPane tabbedPane;
    private EmployeePanel employeePanel;
    private DepartmentPanel departmentPanel;
    private ReportPanel reportPanel;

    public MainFrame() {
        frame = new JFrame("Employee Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        employeePanel = new EmployeePanel();
        departmentPanel = new DepartmentPanel();
        reportPanel = new ReportPanel();

        tabbedPane.addTab("Employees", employeePanel.getPanel());
        tabbedPane.addTab("Departments", departmentPanel.getPanel());
        tabbedPane.addTab("Reports", reportPanel.getPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }

}