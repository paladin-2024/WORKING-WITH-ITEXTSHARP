package gui.TableModel;

import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTableModelTest {
    private EmployeeTableModel model;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        model = new EmployeeTableModel();
        employee1 = new Employee(1, "John Doe", "john@example.com",
                "Engineering", 75000.0,
                Date.valueOf(LocalDate.of(2023, 1, 15)));
        employee2 = new Employee(2, "Jane Smith", "jane@example.com",
                "Marketing", 65000.0,
                Date.valueOf(LocalDate.of(2023, 6, 1)));
    }


    @Test
    void testSetEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        model.setEmployees(employees);

        assertEquals(2, model.getRowCount(), "Should have 2 rows after setting employees");
        assertEquals(employee1, model.getEmployeeAt(0), "First employee should match");
        assertEquals(employee2, model.getEmployeeAt(1), "Second employee should match");
    }

    @Test
    void testGetValueAt() {
        model.setEmployees(List.of(employee1));

        assertEquals(1, model.getValueAt(0, 0), "ID should match");
        assertEquals("John Doe", model.getValueAt(0, 1), "Name should match");
        assertEquals("john@example.com", model.getValueAt(0, 2), "Email should match");
        assertEquals("Engineering", model.getValueAt(0, 3), "Department should match");
        assertEquals(75000.0, model.getValueAt(0, 4), "Salary should match");
        assertEquals(Date.valueOf("2023-01-15"), model.getValueAt(0, 5), "Join date should match");
    }

    @Test
    void testAddEmployee() {
        model.addEmployee(employee1);

        assertEquals(1, model.getRowCount(), "Should have 1 row after add");
        assertEquals(employee1, model.getEmployeeAt(0), "Added employee should match");
    }

    @Test
    void testUpdateEmployee() {
        model.setEmployees(List.of(employee1));
        Employee updatedEmployee = new Employee(1, "John Updated", "john.new@example.com",
                "HR", 80000.0,
                Date.valueOf("2023-03-20"));

        model.updateEmployee(0, updatedEmployee);

        assertEquals("John Updated", model.getValueAt(0, 1), "Name should be updated");
        assertEquals("john.new@example.com", model.getValueAt(0, 2), "Email should be updated");
        assertEquals("HR", model.getValueAt(0, 3), "Department should be updated");
        assertEquals(80000.0, model.getValueAt(0, 4), "Salary should be updated");
        assertEquals(Date.valueOf("2023-03-20"), model.getValueAt(0, 5), "Join date should be updated");
    }

    @Test
    void testRemoveEmployee() {
        model.setEmployees(List.of(employee1, employee2));

        model.removeEmployee(0);

        assertEquals(1, model.getRowCount(), "Should have 1 row after removal");
        assertEquals(employee2, model.getEmployeeAt(0), "Remaining employee should match");
    }

    @Test
    void testClear() {
        model.setEmployees(List.of(employee1, employee2));

        model.clear();

        assertEquals(0, model.getRowCount(), "Should have 0 rows after clear");
    }

    @Test
    void testGetEmployeeAt() {
        model.setEmployees(List.of(employee1, employee2));

        assertEquals(employee1, model.getEmployeeAt(0), "Should return correct employee");
        assertEquals(employee2, model.getEmployeeAt(1), "Should return correct employee");
    }

    @Test
    void testGetEmployeeAtWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                model.getEmployeeAt(0), "Should throw for empty model");

        model.setEmployees(List.of(employee1));
        assertThrows(IndexOutOfBoundsException.class, () ->
                model.getEmployeeAt(1), "Should throw for invalid index");
    }

    @Test
    void testColumnNames() {
        assertEquals("ID", model.getColumnName(0), "First column should be ID");
        assertEquals("Name", model.getColumnName(1), "Second column should be Name");
        assertEquals("Email", model.getColumnName(2), "Third column should be Email");
        assertEquals("Department", model.getColumnName(3), "Fourth column should be Department");
        assertEquals("Salary", model.getColumnName(4), "Fifth column should be Salary");
        assertEquals("Join Date", model.getColumnName(5), "Sixth column should be Join Date");
    }

    @Test
    void testEmptyModelOperations() {
        // Test operations on empty model
        assertEquals(0, model.getRowCount(), "Empty model should have 0 rows");
        assertThrows(IndexOutOfBoundsException.class, () ->
                model.getValueAt(0, 0), "Should throw for empty model");

        // Test clearing empty model
        model.clear();
        assertEquals(0, model.getRowCount(), "Clearing empty model should keep 0 rows");
    }
}