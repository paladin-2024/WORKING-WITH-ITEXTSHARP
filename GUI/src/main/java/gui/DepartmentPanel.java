package gui;

import database.DepartmentDAO;
import model.Department;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DepartmentPanel {
    private final JPanel panel;
    private final JTable departmentTable;
    private final DepartmentTableModel tableModel;
    private final JButton loadDataBtn;
    private final JButton addDepartmentBtn;
    private final JButton editDepartmentBtn;
    private final JButton deleteDepartmentBtn;

    public DepartmentPanel() {
        panel = new JPanel();
        tableModel = new DepartmentTableModel();
        departmentTable = new JTable((TableModel) tableModel);
        loadDataBtn = new JButton("Load Data");
        addDepartmentBtn = new JButton("Add Department");
        editDepartmentBtn = new JButton("Edit Department");
        deleteDepartmentBtn = new JButton("Delete Department");

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
        buttonPanel.add(addDepartmentBtn);
        buttonPanel.add(editDepartmentBtn);
        buttonPanel.add(deleteDepartmentBtn);

        panel.add(new JScrollPane(departmentTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        loadDataBtn.addActionListener(e -> loadData());
        addDepartmentBtn.addActionListener(e -> addDepartment());
        editDepartmentBtn.addActionListener(e -> editDepartment());
        deleteDepartmentBtn.addActionListener(e -> deleteDepartment());
    }

    private void loadData() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        try {
            List<Department> departments = departmentDAO.getAllDepartments();
            tableModel.setDepartments(departments);
            tableModel.fireTableDataChanged();
            showMessage("Department data loaded successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            showMessage("Error loading department data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDepartment() {
        DepartmentDialog dialog = new DepartmentDialog(getParentFrame());
        if (dialog.showDialog()) {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            try {
                if (departmentDAO.addDepartment(dialog.getDepartment())) {
                    loadData();
                    showMessage("Department added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showMessage("Error adding department: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editDepartment() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow < 0) {
            showMessage("Please select a department to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Department department = tableModel.getDepartmentAt(selectedRow);
        DepartmentDialog dialog = new DepartmentDialog(getParentFrame(), department);
        if (dialog.showDialog()) {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            try {
                if (departmentDAO.updateDepartment(dialog.getDepartment())) {
                    loadData();
                    showMessage("Department updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showMessage("Error updating department: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteDepartment() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow < 0) {
            showMessage("Please select a department to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(panel,
                "Are you sure you want to delete this department?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Department department = tableModel.getDepartmentAt(selectedRow);
            DepartmentDAO departmentDAO = new DepartmentDAO();
            try {
                if (departmentDAO.deleteDepartment(department.getId())) {
                    loadData();
                    showMessage("Department deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showMessage("Error deleting department: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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