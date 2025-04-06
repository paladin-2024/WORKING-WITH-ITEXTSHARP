package model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2023, 6, 15);

    @Test
    void testCreateSalaryReport() {
        Report report = Report.createSalaryReport(1, TEST_DATE, 10, 50000.0,
                "Engineering", 5000.0, 3000.0, 7000.0);

        assertEquals(1, report.getId());
        assertEquals("Salary Report", report.getReportType());
        assertEquals(TEST_DATE, report.getGeneratedDate());
        assertEquals(10, report.getEmployeeCount());
        assertEquals(50000.0, report.getTotalSalary());
        assertEquals("Engineering", report.getDepartment());
        assertEquals(5000.0, report.getAvgSalary());
        assertEquals(3000.0, report.getMinSalary());
        assertEquals(7000.0, report.getMaxSalary());
        assertNull(report.getEmployeeName());
        assertNull(report.getEmployeeEmail());
    }

    @Test
    void testCreateNewHireReport() {
        Report report = Report.createNewHireReport(2, TEST_DATE, 60000.0,
                "Marketing", "John Doe", "john@example.com");

        assertEquals(2, report.getId());
        assertEquals("New Hires Report", report.getReportType());
        assertEquals(TEST_DATE, report.getGeneratedDate());
        assertEquals(1, report.getEmployeeCount());
        assertEquals(60000.0, report.getTotalSalary());
        assertEquals("Marketing", report.getDepartment());
        assertEquals("John Doe", report.getEmployeeName());
        assertEquals("john@example.com", report.getEmployeeEmail());
        assertEquals(0.0, report.getAvgSalary()); // Default value when null
        assertEquals(0.0, report.getMinSalary());  // Default value when null
        assertEquals(0.0, report.getMaxSalary());  // Default value when null
    }

    @Test
    void testCreateDepartmentAnalysis() {
        Report report = Report.createDepartmentAnalysis(TEST_DATE, 15, 75000.0,
                "Finance", 100000.0, 0.75);

        assertEquals(0, report.getId()); // Default ID for analysis
        assertEquals("Department Analysis", report.getReportType());
        assertEquals(TEST_DATE, report.getGeneratedDate());
        assertEquals(15, report.getEmployeeCount());
        assertEquals(75000.0, report.getTotalSalary());
        assertEquals("Finance", report.getDepartment());
        assertEquals(100000.0, report.getBudget());
        assertEquals(0.75, report.getUtilization());
        assertNull(report.getEmployeeName());
        assertNull(report.getEmployeeEmail());
    }

    @Test
    void testCreateMonthlyReport() {
        Report report = Report.createMonthlyReport(TEST_DATE, 25, 125000.0, "HR");

        assertEquals(0, report.getId()); // Default ID for monthly report
        assertEquals("Monthly Report", report.getReportType());
        assertEquals(TEST_DATE, report.getGeneratedDate());
        assertEquals(25, report.getEmployeeCount());
        assertEquals(125000.0, report.getTotalSalary());
        assertEquals("HR", report.getDepartment());
        assertEquals(0.0, report.getBudget()); // Default value when null
        assertEquals(0.0, report.getUtilization()); // Default value when null
        assertNull(report.getEmployeeName());
        assertNull(report.getEmployeeEmail());
    }

    @Test
    void testNullMetricsDefaultToZero() {
        // Testing that null metrics are converted to 0.0
        Report report = new Report(1, "Test Report", TEST_DATE, 5, 25000.0,
                "Test Dept", null, null, null, null, null);

        assertEquals(0.0, report.getAvgSalary());
        assertEquals(0.0, report.getMinSalary());
        assertEquals(0.0, report.getMaxSalary());
        assertEquals(0.0, report.getBudget());
        assertEquals(0.0, report.getUtilization());
    }
}