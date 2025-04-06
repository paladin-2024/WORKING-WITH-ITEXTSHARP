package gui.Panels;

import gui.TableModel.DepartmentTableModel;
import model.Department;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentPanelTest {

    @Test
    void testLoadData() {
        // Simulate the setup
        DepartmentPanel departmentPanel = new DepartmentPanel();
        DepartmentTableModel tableModel = (DepartmentTableModel) ((JTable) ((JScrollPane) departmentPanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate department data
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(new Department(1, "IT", "Building A", 100000.0));
        mockDepartments.add(new Department(2, "HR", "Building B", 75000.0));

        // Load mock data into the table model
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(2, tableModel.getRowCount(), "Row count should match the number of departments.");
        assertEquals("IT", tableModel.getValueAt(0, 1), "First department name should match.");
        assertEquals("HR", tableModel.getValueAt(1, 1), "Second department name should match.");
    }

    @Test
    void testAddDepartment() {
        // Simulate the setup
        DepartmentPanel departmentPanel = new DepartmentPanel();
        DepartmentTableModel tableModel = (DepartmentTableModel) ((JTable) ((JScrollPane) departmentPanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate adding a new department
        Department newDepartment = new Department(3, "Marketing", "Building C", 50000.0);
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(newDepartment);
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should reflect the added department.");
        assertEquals("Marketing", tableModel.getValueAt(0, 1), "Added department name should match.");
    }

    @Test
    void testEditDepartment() {
        // Simulate the setup
        DepartmentPanel departmentPanel = new DepartmentPanel();
        DepartmentTableModel tableModel = (DepartmentTableModel) ((JTable) ((JScrollPane) departmentPanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate existing department
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(new Department(1, "Finance", "Building A", 90000.0));
        tableModel.setDepartments(mockDepartments);

        // Simulate editing the department
        Department updatedDepartment = new Department(1, "Finance", "Building D", 95000.0);
        mockDepartments.set(0, updatedDepartment);
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should remain unchanged after editing.");
        assertEquals("Building D", tableModel.getValueAt(0, 2), "Updated location should match.");
        assertEquals(95000.0, tableModel.getValueAt(0, 3), "Updated budget should match.");
    }

    @Test
    void testDeleteDepartment() {
        // Simulate the setup
        DepartmentPanel departmentPanel = new DepartmentPanel();
        DepartmentTableModel tableModel = (DepartmentTableModel) ((JTable) ((JScrollPane) departmentPanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate existing departments
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(new Department(1, "Finance", "Building A", 90000.0));
        mockDepartments.add(new Department(2, "HR", "Building B", 75000.0));
        tableModel.setDepartments(mockDepartments);

        // Simulate deleting a department
        mockDepartments.remove(0); // Remove the first department
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should reflect after deletion.");
        assertEquals("HR", tableModel.getValueAt(0, 1), "Remaining department name should match.");
    }
}
