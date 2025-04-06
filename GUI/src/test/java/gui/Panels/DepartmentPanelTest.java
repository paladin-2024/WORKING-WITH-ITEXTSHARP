package gui.Panels;

import database.DAO.DepartmentDAO;
import gui.TableModel.DepartmentTableModel;
import model.Department;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepartmentPanelTest {

    @Test
    void testLoadData() {
        // Setup
        DepartmentPanel departmentPanel = new DepartmentPanel();

        // Simulate data for testing
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(new Department(1, "HR"));
        mockDepartments.add(new Department(2, "IT"));

        // Simulate loading data
        DepartmentTableModel tableModel = null;
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions to check if data was loaded correctly
        assertEquals(2, tableModel.getRowCount(), "Row count should match the number of departments");
        assertEquals("HR", tableModel.getValueAt(0, 1), "First department name should match");
        assertEquals("IT", tableModel.getValueAt(1, 1), "Second department name should match");
    }

    @Test
    void testAddDepartment() {
        // Setup
        DepartmentPanel departmentPanel = new DepartmentPanel();
        

        // Simulate adding a department
        Department newDepartment = new Department(3, "Finance");
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(newDepartment);

        DepartmentTableModel tableModel = null;
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should reflect the added department");
        assertEquals("Finance", tableModel.getValueAt(0, 1), "Added department name should match");
    }

    @Test
    void testDeleteDepartment() {
        // Setup
        DepartmentPanel departmentPanel = new DepartmentPanel();
       

        // Simulate existing departments
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(new Department(1, "HR"));
        mockDepartments.add(new Department(2, "IT"));

        DepartmentTableModel tableModel = null;
        tableModel.setDepartments(mockDepartments);

        // Simulate deleting a department
        mockDepartments.remove(0); // Remove the first department
        tableModel.setDepartments(mockDepartments);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should reflect after deletion");
        assertEquals("IT", tableModel.getValueAt(0, 1), "Remaining department should match");
    }
}
