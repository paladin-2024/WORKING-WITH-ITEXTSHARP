package database.DAO;

import database.DBConnection;
import model.Report;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportDAOTest {
    private ReportDAO reportDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        // Set up an in-memory H2 database for testing
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        DBConnection.setTestConnection(connection);

        // Create test tables and insert sample data
        try (Statement stmt = connection.createStatement()) {
            // Create departments table
            stmt.execute("CREATE TABLE departments (" +
                    "name VARCHAR(100) PRIMARY KEY, " +
                    "budget DECIMAL(10,2))");

            // Create employees table
            stmt.execute("CREATE TABLE employees (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100), " +
                    "department VARCHAR(100), " +
                    "salary DECIMAL(10,2), " +
                    "join_date DATE)");

            // Insert test data
            stmt.execute("INSERT INTO departments VALUES ('Engineering', 1000000.00)");
            stmt.execute("INSERT INTO departments VALUES ('Marketing', 500000.00)");

            stmt.execute("INSERT INTO employees VALUES " +
                    "(1, 'John Doe', 'john@example.com', 'Engineering', 75000.00, '2023-01-15')");
            stmt.execute("INSERT INTO employees VALUES " +
                    "(2, 'Jane Smith', 'jane@example.com', 'Engineering', 85000.00, '2023-06-20')");
            stmt.execute("INSERT INTO employees VALUES " +
                    "(3, 'Mike Johnson', 'mike@example.com', 'Marketing', 65000.00, '2023-11-01')");
            stmt.execute("INSERT INTO employees VALUES " +
                    "(4, 'New Hire', 'new@example.com', 'Marketing', 60000.00, CURRENT_DATE - 10)");
        }

        reportDAO = new ReportDAO();
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE employees");
            stmt.execute("DROP TABLE departments");
        }
        connection.close();
        DBConnection.clearTestConnection();
    }

    @Test
    void testGenerateSalaryReport() throws Exception {
        List<Report> reports = reportDAO.generateSalaryReport();

        assertEquals(2, reports.size());

        // Verify Engineering department report
        Report engReport = reports.stream()
                .filter(r -> r.getDepartment().equals("Engineering"))
                .findFirst()
                .orElseThrow();

        assertEquals("Salary Report", engReport.getReportType());
        assertEquals(2, engReport.getEmployeeCount());
        assertEquals(160000.00, engReport.getTotalSalary(), 0.001);
        assertEquals(80000.00, engReport.getAvgSalary(), 0.001);
        assertEquals(75000.00, engReport.getMinSalary(), 0.001);
        assertEquals(85000.00, engReport.getMaxSalary(), 0.001);

        // Verify Marketing department report
        Report mktReport = reports.stream()
                .filter(r -> r.getDepartment().equals("Marketing"))
                .findFirst()
                .orElseThrow();

        assertEquals(2, mktReport.getEmployeeCount());
    }

    @Test
    void testGenerateNewHiresReport() throws Exception {
        List<Report> reports = reportDAO.generateNewHiresReport();

        // Should only include the employee hired in last 30 days
        assertEquals(1, reports.size());

        Report report = reports.get(0);
        assertEquals("New Hires Report", report.getReportType());
        assertEquals("New Hire", report.getEmployeeName());
        assertEquals("new@example.com", report.getEmployeeEmail());
        assertEquals("Marketing", report.getDepartment());
        assertEquals(60000.00, report.getTotalSalary(), 0.001);
    }

    @Test
    void testGenerateDepartmentAnalysis() throws Exception {
        List<Report> reports = reportDAO.generateDepartmentAnalysis();

        assertEquals(2, reports.size());

        // Verify Engineering department analysis
        Report engReport = reports.stream()
                .filter(r -> r.getDepartment().equals("Engineering"))
                .findFirst()
                .orElseThrow();

        assertEquals("Department Analysis", engReport.getReportType());
        assertEquals(2, engReport.getEmployeeCount());
        assertEquals(160000.00, engReport.getTotalSalary(), 0.001);
        assertEquals(1000000.00, engReport.getBudget(), 0.001);
        assertEquals(16.00, engReport.getUtilization(), 0.01); // 16% utilization

        // Verify Marketing department analysis
        Report mktReport = reports.stream()
                .filter(r -> r.getDepartment().equals("Marketing"))
                .findFirst()
                .orElseThrow();

        assertEquals(2, mktReport.getEmployeeCount());
        assertEquals(125000.00, mktReport.getTotalSalary(), 0.001);
        assertEquals(500000.00, mktReport.getBudget(), 0.001);
    }

    @Test
    void testGenerateMonthlyReport() throws Exception {
        List<Report> reports = reportDAO.generateMonthlyReport();

        // Should group by month and department
        assertTrue(reports.size() >= 2);

        // Verify January 2023 report
        Report janReport = reports.stream()
                .filter(r -> r.getGeneratedDate().equals(LocalDate.of(2023, 1, 1)))
                .findFirst()
                .orElseThrow();

        assertEquals("Monthly Report", janReport.getReportType());
        assertEquals(1, janReport.getEmployeeCount());
        assertEquals(75000.00, janReport.getTotalSalary(), 0.001);
        assertEquals("Engineering", janReport.getDepartment());
    }

    @Test
    void testEmptyReports() throws Exception {
        // Clear test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM employees");
            stmt.execute("DELETE FROM departments");
        }

        // All reports should be empty
        assertTrue(reportDAO.generateSalaryReport().isEmpty());
        assertTrue(reportDAO.generateNewHiresReport().isEmpty());
        assertTrue(reportDAO.generateDepartmentAnalysis().isEmpty());
        assertTrue(reportDAO.generateMonthlyReport().isEmpty());
    }
}