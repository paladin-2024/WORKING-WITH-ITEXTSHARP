package gui.Panels;

import model.Employee;
import gui.TableModel.EmployeeTableModel;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeePanelTest {

    @Test
    void testLoadData() {
        // Simulate the setup
        EmployeePanel employeePanel = new EmployeePanel();
        EmployeeTableModel tableModel = (EmployeeTableModel) ((JTable) ((JScrollPane) employeePanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate employee data
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(new Employee(1, "John Doe", "john.doe@example.com", "Finance", 60000.0, Date.valueOf("2022-01-15")));
        mockEmployees.add(new Employee(2, "Jane Smith", "jane.smith@example.com", "HR", 50000.0, Date.valueOf("2023-02-20")));

        // Load mock data into the table model
        tableModel.setEmployees(mockEmployees);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(2, tableModel.getRowCount(), "Row count should match the number of employees.");
        assertEquals("John Doe", tableModel.getValueAt(0, 1), "First employee name should match.");
        assertEquals("Jane Smith", tableModel.getValueAt(1, 1), "Second employee name should match.");
    }

    @Test
    void testAddEmployee() {
        // Simulate the setup
        EmployeePanel employeePanel = new EmployeePanel();
        EmployeeTableModel tableModel = (EmployeeTableModel) ((JTable) ((JScrollPane) employeePanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate adding a new employee
        Employee newEmployee = new Employee(3, "Alice Johnson", "alice.johnson@example.com", "Marketing", 55000.0, Date.valueOf("2021-03-05"));
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(newEmployee);
        tableModel.setEmployees(mockEmployees);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should reflect the added employee.");
        assertEquals("Alice Johnson", tableModel.getValueAt(0, 1), "Added employee name should match.");
    }

    @Test
    void testDeleteEmployee() {
        // Simulate the setup
        EmployeePanel employeePanel = new EmployeePanel();
        EmployeeTableModel tableModel = (EmployeeTableModel) ((JTable) ((JScrollPane) employeePanel.getPanel().getComponent(0)).getViewport().getView()).getModel();

        // Simulate existing employees
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(new Employee(1, "John Doe", "john.doe@example.com", "Finance", 60000.0, Date.valueOf("2022-01-15")));
        mockEmployees.add(new Employee(2, "Jane Smith", "jane.smith@example.com", "HR", 50000.0, Date.valueOf("2023-02-20")));

        tableModel.setEmployees(mockEmployees);

        // Simulate deleting an employee
        mockEmployees.remove(0); // Remove the first employee
        tableModel.setEmployees(mockEmployees);
        tableModel.fireTableDataChanged();

        // Assertions
        assertEquals(1, tableModel.getRowCount(), "Row count should reflect after deletion.");
        assertEquals("Jane Smith", tableModel.getValueAt(0, 1), "Remaining employee name should match.");
    }
}
