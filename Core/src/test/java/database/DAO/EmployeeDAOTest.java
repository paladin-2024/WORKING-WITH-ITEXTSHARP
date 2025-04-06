package database.DAO;

import model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize EmployeeDAO instance
        employeeDAO = new EmployeeDAO();

        // Establish connection to the database
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_management", "root", "2006"
        );

        // Clean the table before each test
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE employees");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close the database connection after each test
        connection.close();
    }

    @Test
    void testGetAllEmployees() throws SQLException {
        // Insert sample employees into the database
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO employees (name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "john.doe@example.com");
            pstmt.setString(3, "Finance");
            pstmt.setDouble(4, 60000.0);
            pstmt.setDate(5, Date.valueOf("2022-01-15"));
            pstmt.executeUpdate();

            pstmt.setString(1, "Jane Smith");
            pstmt.setString(2, "jane.smith@example.com");
            pstmt.setString(3, "HR");
            pstmt.setDouble(4, 50000.0);
            pstmt.setDate(5, Date.valueOf("2023-02-20"));
            pstmt.executeUpdate();
        }

        // Retrieve all employees using DAO
        List<Employee> employees = employeeDAO.getAllEmployees();

        // Assertions
        assertEquals(2, employees.size(), "Should retrieve 2 employees.");
        assertEquals("John Doe", employees.get(0).getName(), "First employee's name should match.");
        assertEquals("Jane Smith", employees.get(1).getName(), "Second employee's name should match.");
    }

    @Test
    void testAddEmployee() throws SQLException {
        // Create a new employee
        Employee employee = new Employee(0, "Alice Johnson", "alice.johnson@example.com", "Marketing", 55000.0, Date.valueOf("2021-03-05"));

        // Add the employee using DAO
        boolean result = employeeDAO.addEmployee(employee);

        // Assertions
        assertTrue(result, "Employee should be added successfully.");

        // Verify the employee in the database
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM employees WHERE email = ?")) {
            pstmt.setString(1, "alice.johnson@example.com");
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "Added employee should exist in the database.");
                assertEquals("Marketing", rs.getString("department"), "Department should match.");
                assertEquals(55000.0, rs.getDouble("salary"), "Salary should match.");
            }
        }
    }

    @Test
    void testUpdateEmployee() throws SQLException {
        // Insert a sample employee
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO employees (id, name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "John Doe");
            pstmt.setString(3, "john.doe@example.com");
            pstmt.setString(4, "Finance");
            pstmt.setDouble(5, 60000.0);
            pstmt.setDate(6, Date.valueOf("2022-01-15"));
            pstmt.executeUpdate();
        }

        // Update the employee using DAO
        Employee updatedEmployee = new Employee(1, "John Doe", "john.doe@example.com", "Operations", 65000.0, Date.valueOf("2022-01-15"));
        boolean result = employeeDAO.updateEmployee(updatedEmployee);

        // Assertions
        assertTrue(result, "Employee should be updated successfully.");

        // Verify the updated employee in the database
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "Updated employee should exist in the database.");
                assertEquals("Operations", rs.getString("department"), "Updated department should match.");
                assertEquals(65000.0, rs.getDouble("salary"), "Updated salary should match.");
            }
        }
    }

    @Test
    void testDeleteEmployee() throws SQLException {
        // Insert a sample employee
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO employees (id, name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "John Doe");
            pstmt.setString(3, "john.doe@example.com");
            pstmt.setString(4, "Finance");
            pstmt.setDouble(5, 60000.0);
            pstmt.setDate(6, Date.valueOf("2022-01-15"));
            pstmt.executeUpdate();
        }

        // Delete the employee using DAO
        boolean result = employeeDAO.deleteEmployee(1);

        // Assertions
        assertTrue(result, "Employee should be deleted successfully.");

        // Verify the employee no longer exists in the database
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                assertFalse(rs.next(), "Deleted employee should not exist in the database.");
            }
        }
    }
}
