package database.DAO;

import model.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDAOTest {

    private DepartmentDAO departmentDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Set up DepartmentDAO instance
        departmentDAO = new DepartmentDAO();

        // Use DriverManager to establish connection
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_management", "root", "2006"
        );

        // Clean the table before each test
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE departments");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close the connection after each test
        connection.close();
    }

    @Test
    void testGetAllDepartments() throws SQLException {
        // Insert sample data into the database
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO departments (name, location, budget) VALUES (?, ?, ?)")) {
            pstmt.setString(1, "Finance");
            pstmt.setString(2, "Building A");
            pstmt.setDouble(3, 100000.0);
            pstmt.executeUpdate();

            pstmt.setString(1, "HR");
            pstmt.setString(2, "Building B");
            pstmt.setDouble(3, 75000.0);
            pstmt.executeUpdate();
        }

        // Call the DAO method to fetch all departments
        List<Department> departments = departmentDAO.getAllDepartments();

        // Assertions
        assertEquals(2, departments.size(), "Should retrieve 2 departments");
        assertEquals("Finance", departments.get(0).getName(), "First department name should match");
        assertEquals("HR", departments.get(1).getName(), "Second department name should match");
    }

    @Test
    void testAddDepartment() throws SQLException {
        // Create a new department instance
        Department department = new Department(0, "IT", "Building C", 50000.0);

        // Add the department using the DAO
        boolean result = departmentDAO.addDepartment(department);

        // Assertions
        assertTrue(result, "Department should be added successfully");

        // Verify the department in the database
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM departments WHERE name = ?")) {
            pstmt.setString(1, "IT");
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "Added department should exist in the database");
                assertEquals("Building C", rs.getString("location"), "Location should match");
                assertEquals(50000.0, rs.getDouble("budget"), "Budget should match");
            }
        }
    }

    @Test
    void testUpdateDepartment() throws SQLException {
        // Insert sample department into the database
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO departments (id, name, location, budget) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Finance");
            pstmt.setString(3, "Building A");
            pstmt.setDouble(4, 100000.0);
            pstmt.executeUpdate();
        }

        // Update the department using the DAO
        Department updatedDepartment = new Department(1, "Finance", "Building D", 110000.0);
        boolean result = departmentDAO.updateDepartment(updatedDepartment);

        // Assertions
        assertTrue(result, "Department should be updated successfully");

        // Verify the updated department in the database
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM departments WHERE id = ?")) {
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "Updated department should exist in the database");
                assertEquals("Building D", rs.getString("location"), "Updated location should match");
                assertEquals(110000.0, rs.getDouble("budget"), "Updated budget should match");
            }
        }
    }

    @Test
    void testDeleteDepartment() throws SQLException {
        // Insert sample department into the database
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO departments (id, name, location, budget) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Finance");
            pstmt.setString(3, "Building A");
            pstmt.setDouble(4, 100000.0);
            pstmt.executeUpdate();
        }

        // Delete the department using the DAO
        boolean result = departmentDAO.deleteDepartment(1);

        // Assertions
        assertTrue(result, "Department should be deleted successfully");

        // Verify the department no longer exists in the database
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM departments WHERE id = ?")) {
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                assertFalse(rs.next(), "Deleted department should not exist in the database");
            }
        }
    }
}
