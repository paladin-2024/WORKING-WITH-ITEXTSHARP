package database.DAO;

import database.DBConnection;
import model.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDAOTest {
    private DepartmentDAO departmentDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Set up an in-memory H2 database for testing
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");


        // Create the test table
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE departments (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "location VARCHAR(100), " +
                    "budget DECIMAL(10,2))");
        }

        departmentDAO = new DepartmentDAO();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Drop the test table and close connection
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE departments");
        }
        connection.close();
    }

    @Test
    void testGetAllDepartmentsWhenEmpty() throws SQLException {
        List<Department> departments = departmentDAO.getAllDepartments();
        assertTrue(departments.isEmpty());
    }

    @Test
    void testAddDepartment() throws SQLException {
        Department department = new Department(0, "Engineering", "Building A", 100000.0);
        boolean result = departmentDAO.addDepartment(department);
        assertTrue(result);

        List<Department> departments = departmentDAO.getAllDepartments();
        assertEquals(1, departments.size());
        assertEquals("Engineering", departments.get(0).getName());
    }

    @Test
    void testUpdateDepartment() throws SQLException {
        // Add a department first
        Department department = new Department(0, "HR", "Building B", 50000.0);
        departmentDAO.addDepartment(department);

        // Get the ID (assuming it's auto-incremented to 1)
        List<Department> departments = departmentDAO.getAllDepartments();
        int id = departments.get(0).getId();

        // Update the department
        Department updatedDept = new Department(id, "Human Resources", "Building C", 60000.0);
        boolean result = departmentDAO.updateDepartment(updatedDept);
        assertTrue(result);

        // Verify the update
        departments = departmentDAO.getAllDepartments();
        assertEquals(1, departments.size());
        assertEquals("Human Resources", departments.get(0).getName());
        assertEquals("Building C", departments.get(0).getLocation());
    }

    @Test
    void testDeleteDepartment() throws SQLException {
        // Add a department first
        Department department = new Department(0, "Finance", "Building D", 75000.0);
        departmentDAO.addDepartment(department);

        // Get the ID
        List<Department> departments = departmentDAO.getAllDepartments();
        int id = departments.get(0).getId();

        // Delete the department
        boolean result = departmentDAO.deleteDepartment(id);
        assertTrue(result);

        // Verify deletion
        departments = departmentDAO.getAllDepartments();
        assertTrue(departments.isEmpty());
    }

    @Test
    void testAddDepartmentWithInvalidData() {
        // Test with null name (should throw SQLException)
        Department department = new Department(0, null, "Building E", 80000.0);
        assertThrows(SQLException.class, () -> departmentDAO.addDepartment(department));
    }
}