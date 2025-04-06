package gui.Panels;

import model.Report;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportPanelTest {

    @Test
    void testCreateSalaryReport() {
        // Test input
        int id = 1;
        LocalDate generatedDate = LocalDate.now();
        int employeeCount = 100;
        double totalSalary = 500000.0;
        String department = "Finance";
        double avgSalary = 5000.0;
        double minSalary = 3000.0;
        double maxSalary = 8000.0;

        // Invoke the factory method
        Report report = Report.createSalaryReport(id, generatedDate, employeeCount, totalSalary, department, avgSalary, minSalary, maxSalary);

        // Assertions
        assertEquals(id, report.getId(), "ID should match");
        assertEquals("Salary Report", report.getReportType(), "Report type should be 'Salary Report'");
        assertEquals(generatedDate, report.getGeneratedDate(), "Generated date should match");
        assertEquals(employeeCount, report.getEmployeeCount(), "Employee count should match");
        assertEquals(totalSalary, report.getTotalSalary(), "Total salary should match");
        assertEquals(department, report.getDepartment(), "Department should match");
        assertEquals(avgSalary, report.getAvgSalary(), "Average salary should match");
        assertEquals(minSalary, report.getMinSalary(), "Minimum salary should match");
        assertEquals(maxSalary, report.getMaxSalary(), "Maximum salary should match");
    }

    @Test
    void testCreateNewHireReport() {
        // Test input
        int id = 2;
        LocalDate generatedDate = LocalDate.now();
        double salary = 4500.0;
        String department = "HR";
        String employeeName = "John Doe";
        String employeeEmail = "john.doe@example.com";

        // Invoke the factory method
        Report report = Report.createNewHireReport(id, generatedDate, salary, department, employeeName, employeeEmail);

        // Assertions
        assertEquals(id, report.getId(), "ID should match");
        assertEquals("New Hires Report", report.getReportType(), "Report type should be 'New Hires Report'");
        assertEquals(generatedDate, report.getGeneratedDate(), "Generated date should match");
        assertEquals(1, report.getEmployeeCount(), "Employee count should be 1 for a new hire");
        assertEquals(salary, report.getTotalSalary(), "Salary should match");
        assertEquals(department, report.getDepartment(), "Department should match");
        assertEquals(employeeName, report.getEmployeeName(), "Employee name should match");
        assertEquals(employeeEmail, report.getEmployeeEmail(), "Employee email should match");
    }

    @Test
    void testCreateDepartmentAnalysis() {
        // Test input
        LocalDate generatedDate = LocalDate.now();
        int employeeCount = 50;
        double totalSalary = 200000.0;
        String department = "Marketing";
        double budget = 300000.0;
        double utilization = 0.67;

        // Invoke the factory method
        Report report = Report.createDepartmentAnalysis(generatedDate, employeeCount, totalSalary, department, budget, utilization);

        // Assertions
        assertEquals("Department Analysis", report.getReportType(), "Report type should be 'Department Analysis'");
        assertEquals(generatedDate, report.getGeneratedDate(), "Generated date should match");
        assertEquals(employeeCount, report.getEmployeeCount(), "Employee count should match");
        assertEquals(totalSalary, report.getTotalSalary(), "Total salary should match");
        assertEquals(department, report.getDepartment(), "Department should match");
        assertEquals(budget, report.getBudget(), "Budget should match");
        assertEquals(utilization, report.getUtilization(), "Utilization should match");
    }

    @Test
    void testCreateMonthlyReport() {
        // Test input
        LocalDate generatedDate = LocalDate.now();
        int employeeCount = 30;
        double totalSalary = 120000.0;
        String department = "Operations";

        // Invoke the factory method
        Report report = Report.createMonthlyReport(generatedDate, employeeCount, totalSalary, department);

        // Assertions
        assertEquals("Monthly Report", report.getReportType(), "Report type should be 'Monthly Report'");
        assertEquals(generatedDate, report.getGeneratedDate(), "Generated date should match");
        assertEquals(employeeCount, report.getEmployeeCount(), "Employee count should match");
        assertEquals(totalSalary, report.getTotalSalary(), "Total salary should match");
        assertEquals(department, report.getDepartment(), "Department should match");
    }
}
