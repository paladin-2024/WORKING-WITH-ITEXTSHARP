package gui;

import database.EmployeeDAO;
import models.Employee;
import utils.TableModelUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoadDataPanel extends JPanel {
    private JTable employeeTable;
    private JButton loadDataButton;

    public LoadDataPanel(JTable table) {
        this.employeeTable = table;

        // Initialize the button
        loadDataButton = new JButton("Load Data");

        // Add action listener for loading data
        loadDataButton.addActionListener(e -> loadEmployeeData());

        // Configure the layout
        setLayout(new BorderLayout());
        add(loadDataButton, BorderLayout.CENTER);
    }

    // Load employee data into the JTable
    private void loadEmployeeData() {
        List<Employee> employees = new EmployeeDAO().getEmployees();
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No data found in the database!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Set the data to the JTable using TableModelUtil
        employeeTable.setModel(new TableModelUtil(employees));
        JOptionPane.showMessageDialog(this,
                "Data successfully loaded.",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
