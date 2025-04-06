package database.DAO;

import database.DBConnection;
import model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {
    private EmployeeDAO employeeDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Set up an in-memory H2 database for testing
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        // Initialize the DBConnection with our test connection
        DBConnection.setTestConnection(connection);

        // Create the test table
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE employees (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100), " +
                    "department VARCHAR(100), " +
                    "salary DECIMAL(10,2), " +
                    "join_date DATE)");
        }

        employeeDAO = new EmployeeDAO();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Drop the test table and close connection
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE employees");
        }
        connection.close();
        DBConnection.clearTestConnection();
    }

    @Test
    void testGetAllEmployeesWhenEmpty() throws SQLException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        assertTrue(employees.isEmpty());
    }

    @Test
    void testAddEmployee() throws SQLException {
        Employee employee = new Employee(0, "John Doe", "john@example.com",
                "Engineering", 75000.0, (java.sql.Date) new Date());

        boolean result = employeeDAO.addEmployee(employee);
        assertTrue(result);

        List<Employee> employees = employeeDAO.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
        assertEquals("john@example.com", employees.get(0).getEmail());
    }

    @Test
    void testUpdateEmployee() throws SQLException {
        // Add an employee first
        Employee employee = new Employee(0, "Jane Smith", "jane@example.com",
                "Marketing", 65000.0, (java.sql.Date) new Date());
        employeeDAO.addEmployee(employee);

        // Get the ID (assuming it's auto-incremented to 1)
        List<Employee> employees = employeeDAO.getAllEmployees();
        int id = employees.get(0).getId();

        // Update the employee
        Employee updatedEmployee = new Employee(id, "Jane Doe", "janedoe@example.com",
                "HR", 70000.0, (java.sql.Date) new Date());
        boolean result = employeeDAO.updateEmployee(updatedEmployee);
        assertTrue(result);

        // Verify the update
        employees = employeeDAO.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("Jane Doe", employees.get(0).getName());
        assertEquals("janedoe@example.com", employees.get(0).getEmail());
        assertEquals("HR", employees.get(0).getDepartment());
    }

    @Test
    void testDeleteEmployee() throws SQLException {
        // Add an employee first
        Employee employee = new Employee(0, "Mike Johnson", "mike@example.com",
                "Finance", 80000.0, (java.sql.Date) new Date());
        employeeDAO.addEmployee(employee);

        // Get the ID
        List<Employee> employees = employeeDAO.getAllEmployees();
        int id = employees.get(0).getId();

        // Delete the employee
        boolean result = employeeDAO.deleteEmployee(id);
        assertTrue(result);

        // Verify deletion
        employees = employeeDAO.getAllEmployees();
        assertTrue(employees.isEmpty());
    }

    @Test
    void testAddEmployeeWithNullValues() {
        // Test with null name (should throw SQLException)
        Employee employee = new Employee(0, (String) null, "test@example.com",
                "IT", 60000.0, (java.sql.Date) new Date());
        assertThrows(SQLException.class, () -> employeeDAO.addEmployee(employee));
    }

    @Test
    void testGetAllEmployeesWithMultipleRecords() throws SQLException {
        // Add multiple employees
        employeeDAO.addEmployee(new Employee(0, "Alice", "alice@example.com",
                "Engineering", 85000.0, (java.sql.Date) new Date()));
        employeeDAO.addEmployee(new Employee(0, "Bob", "bob@example.com",
                "Marketing", 75000.0, (java.sql.Date) new Date()));

        List<Employee> employees = employeeDAO.getAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    void testUpdateNonExistentEmployee() throws SQLException {
        Employee employee = new Employee(999, "Nonexistent", "none@example.com",
                "IT", 50000.0, (java.sql.Date) new Date());
        boolean result = employeeDAO.updateEmployee(employee);
        assertFalse(result, "Should return false when updating non-existent employee");
    }

    @Test
    void testDeleteNonExistentEmployee() throws SQLException {
        boolean result = employeeDAO.deleteEmployee(999);
        assertFalse(result, "Should return false when deleting non-existent employee");
    }
}