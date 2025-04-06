package database.DAO;

import model.Report;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportDAOTest {

    private ReportDAO reportDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        reportDAO = new ReportDAO();

        // Establish connection using DriverManager
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_management", "root", "2006"
        );

        // Clean up the relevant tables before each test
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE employees");
            stmt.execute("TRUNCATE TABLE departments");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testGenerateSalaryReport() throws SQLException {
        // Insert sample employee data
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
            pstmt.setString(3, "Finance");
            pstmt.setDouble(4, 50000.0);
            pstmt.setDate(5, Date.valueOf("2023-02-20"));
            pstmt.executeUpdate();
        }

        // Generate salary report using DAO
        List<Report> reports = reportDAO.generateSalaryReport();

        // Assertions
        assertEquals(1, reports.size(), "Should generate report for one department.");
        Report financeReport = reports.get(0);
        assertEquals("Finance", financeReport.getDepartment(), "Department should be 'Finance'.");
        assertEquals(2, financeReport.getEmployeeCount(), "Employee count should match.");
        assertEquals(110000.0, financeReport.getTotalSalary(), "Total salary should match.");
        assertEquals(60000.0, financeReport.getMaxSalary(), "Max salary should match.");
        assertEquals(50000.0, financeReport.getMinSalary(), "Min salary should match.");
    }

    @Test
    void testGenerateNewHiresReport() throws SQLException {
        // Insert sample employee data
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO employees (id, name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Alice Johnson");
            pstmt.setString(3, "alice.johnson@example.com");
            pstmt.setString(4, "Marketing");
            pstmt.setDouble(5, 55000.0);
            pstmt.setDate(6, Date.valueOf(LocalDate.now().minusDays(15).toString()));
            pstmt.executeUpdate();
        }

        // Generate new hires report using DAO
        List<Report> reports = reportDAO.generateNewHiresReport();

        // Assertions
        assertEquals(1, reports.size(), "Should generate report for one new hire.");
        Report newHireReport = reports.get(0);
        assertEquals("Alice Johnson", newHireReport.getEmployeeName(), "Employee name should match.");
        assertEquals("alice.johnson@example.com", newHireReport.getEmployeeEmail(), "Employee email should match.");
        assertEquals(55000.0, newHireReport.getTotalSalary(), "Salary should match.");
        assertEquals("Marketing", newHireReport.getDepartment(), "Department should match.");
    }

    @Test
    void testGenerateDepartmentAnalysis() throws SQLException {
        // Insert sample department and employee data
        try (PreparedStatement deptStmt = connection.prepareStatement(
                "INSERT INTO departments (name, budget) VALUES (?, ?)")) {
            deptStmt.setString(1, "Finance");
            deptStmt.setDouble(2, 200000.0);
            deptStmt.executeUpdate();
        }
        try (PreparedStatement empStmt = connection.prepareStatement(
                "INSERT INTO employees (name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?)")) {
            empStmt.setString(1, "John Doe");
            empStmt.setString(2, "john.doe@example.com");
            empStmt.setString(3, "Finance");
            empStmt.setDouble(4, 100000.0);
            empStmt.setDate(5, Date.valueOf("2022-01-15"));
            empStmt.executeUpdate();
        }

        // Generate department analysis report using DAO
        List<Report> reports = reportDAO.generateDepartmentAnalysis();

        // Assertions
        assertEquals(1, reports.size(), "Should generate report for one department.");
        Report financeAnalysis = reports.get(0);
        assertEquals("Finance", financeAnalysis.getDepartment(), "Department should match.");
        assertEquals(100000.0, financeAnalysis.getTotalSalary(), "Total salary should match.");
        assertEquals(200000.0, financeAnalysis.getBudget(), "Budget should match.");
        assertEquals(50.0, financeAnalysis.getUtilization(), "Utilization should match.");
    }

    @Test
    void testGenerateMonthlyReport() throws SQLException {
        // Insert sample employee data
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO employees (name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "john.doe@example.com");
            pstmt.setString(3, "Finance");
            pstmt.setDouble(4, 60000.0);
            pstmt.setDate(5, Date.valueOf("2022-01-15"));
            pstmt.executeUpdate();
        }

        // Generate monthly report using DAO
        List<Report> reports = reportDAO.generateMonthlyReport();

        // Assertions
        assertEquals(1, reports.size(), "Should generate report for one month and one department.");
        Report monthlyReport = reports.get(0);
        assertEquals(LocalDate.parse("2022-01-01"), monthlyReport.getGeneratedDate(), "Generated date should match.");
        assertEquals("Finance", monthlyReport.getDepartment(), "Department should match.");
        assertEquals(60000.0, monthlyReport.getTotalSalary(), "Total salary should match.");
    }
}
