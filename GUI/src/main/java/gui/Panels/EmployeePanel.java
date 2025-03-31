package gui.Panels;

import database.DAO.EmployeeDAO;
import gui.TableModel.EmployeeTableModel;
import gui.dialog.EmployeeDialog;
import model.Employee;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeePanel {
    private final JPanel panel;
    private final JTable employeeTable;
    private final EmployeeTableModel tableModel;
    private final JButton loadDataBtn;
    private final JButton addEmployeeBtn;
    private final JButton editEmployeeBtn;
    private final JButton deleteEmployeeBtn;

    public EmployeePanel() {
        panel = new JPanel();
        tableModel = new EmployeeTableModel();
        employeeTable = new JTable(tableModel);
        loadDataBtn = new JButton("Load Data");
        addEmployeeBtn = new JButton("Add Employee");
        editEmployeeBtn = new JButton("Edit Employee");
        deleteEmployeeBtn = new JButton("Delete Employee");

        initialize();
    }

    private void initialize() {
        setupLayout();
        setupListeners();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void setupLayout() {
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loadDataBtn);
        buttonPanel.add(addEmployeeBtn);
        buttonPanel.add(editEmployeeBtn);
        buttonPanel.add(deleteEmployeeBtn);

        panel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        loadDataBtn.addActionListener(e -> loadData());
        addEmployeeBtn.addActionListener(e -> addEmployee());
        editEmployeeBtn.addActionListener(e -> editEmployee());
        deleteEmployeeBtn.addActionListener(e -> deleteEmployee());
    }

    private void loadData() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            tableModel.setEmployees(employees);
            tableModel.fireTableDataChanged();
            showMessage("Data loaded successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            showMessage("Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        EmployeeDialog dialog = new EmployeeDialog(getParentFrame());
        if (dialog.showDialog()) {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            try {
                if (employeeDAO.addEmployee(dialog.getEmployee())) {
                    loadData();
                    showMessage("Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showMessage("Error adding employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            showMessage("Please select an employee to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Employee employee = tableModel.getEmployeeAt(selectedRow);
        EmployeeDialog dialog = new EmployeeDialog(getParentFrame(), employee);
        if (dialog.showDialog()) {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            try {
                if (employeeDAO.updateEmployee(dialog.getEmployee())) {
                    loadData();
                    showMessage("Employee updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showMessage("Error updating employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            showMessage("Please select an employee to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(panel,
                "Are you sure you want to delete this employee?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Employee employee = tableModel.getEmployeeAt(selectedRow);
            EmployeeDAO employeeDAO = new EmployeeDAO();
            try {
                if (employeeDAO.deleteEmployee(employee.getId())) {
                    loadData();
                    showMessage("Employee deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showMessage("Error deleting employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JFrame getParentFrame() {
        return (JFrame)SwingUtilities.getWindowAncestor(panel);
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(panel, message, title, messageType);
    }
}