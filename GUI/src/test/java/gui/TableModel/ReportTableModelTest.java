package gui.TableModel;

import model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportTableModelTest {
    private ReportTableModel model;
    private Report salaryReport;
    private Report newHireReport;
    private Report deptAnalysisReport;
    private Report monthlyReport;

    @BeforeEach
    void setUp() {
        model = new ReportTableModel();

        salaryReport = Report.createSalaryReport(
                1, LocalDate.of(2023, 6, 15),
                10, 500000.0, "Engineering",
                50000.0, 30000.0, 70000.0);

        newHireReport = Report.createNewHireReport(
                2, LocalDate.of(2023, 6, 1),
                65000.0, "Marketing",
                "Jane Smith", "jane@example.com");

        deptAnalysisReport = Report.createDepartmentAnalysis(
                LocalDate.of(2023, 6, 1),
                15, 750000.0, "Finance",
                1000000.0, 75.0);

        monthlyReport = Report.createMonthlyReport(
                LocalDate.of(2023, 1, 1),
                25, 1250000.0, "HR");
    }

    @Test
    void testInitialState() {
        assertEquals(0, model.getRowCount(), "Initially should have 0 rows");
        assertEquals(0, model.getColumnCount(), "Initially should have 0 columns");
    }
    

    @Test
    void testSalaryReportValues() {
        model.setReports(List.of(salaryReport));

        assertEquals("Engineering", model.getValueAt(0, 0), "Department should match");
        assertEquals(10, model.getValueAt(0, 1), "Employee count should match");
        assertEquals("$500,000.00", model.getValueAt(0, 2), "Total salary should be formatted");
        assertEquals("$50,000.00", model.getValueAt(0, 3), "Avg salary should be formatted");
        assertEquals("$30,000.00", model.getValueAt(0, 4), "Min salary should be formatted");
        assertEquals("$70,000.00", model.getValueAt(0, 5), "Max salary should be formatted");
    }

    @Test
    void testNewHireReportValues() {
        model.setReports(List.of(newHireReport));

        assertEquals("Jane Smith", model.getValueAt(0, 0), "Name should match");
        assertEquals("jane@example.com", model.getValueAt(0, 1), "Email should match");
        assertEquals("Marketing", model.getValueAt(0, 2), "Department should match");
        assertEquals("01 Jun 2023", model.getValueAt(0, 3), "Join date should be formatted");
        assertEquals("$65,000.00", model.getValueAt(0, 4), "Salary should be formatted");
    }

    @Test
    void testDepartmentAnalysisValues() {
        model.setReports(List.of(deptAnalysisReport));

        assertEquals("Finance", model.getValueAt(0, 0), "Department should match");
        assertEquals(15, model.getValueAt(0, 1), "Employee count should match");
        assertEquals("$750,000.00", model.getValueAt(0, 2), "Total salary should be formatted");
        assertEquals("$1,000,000.00", model.getValueAt(0, 3), "Budget should be formatted");
        assertEquals("75.00%", model.getValueAt(0, 4), "Utilization should be formatted");
    }

    @Test
    void testMonthlyReportValues() {
        model.setReports(List.of(monthlyReport));

        assertEquals("Jan 2023", model.getValueAt(0, 0), "Month should be formatted");
        assertEquals("HR", model.getValueAt(0, 1), "Department should match");
        assertEquals(25, model.getValueAt(0, 2), "Employee count should match");
        assertEquals("$1,250,000.00", model.getValueAt(0, 3), "Total salary should be formatted");
    }

    @Test
    void testEmptyReports() {
        model.setReports(List.of());

        assertEquals(0, model.getRowCount(), "Should have 0 rows for empty list");
        assertEquals(0, model.getColumnCount(), "Should have 0 columns for empty list");
    }

    @Test
    void testUnknownReportType() {
        Report unknownReport = new Report(0, "Unknown", LocalDate.now(), 0, (double) 0, (String) null, (double) 0, (double) 0, (double) 0, null, null);
        model.setReports(List.of(unknownReport));

        assertEquals(1, model.getRowCount(), "Should have 1 row");
        assertEquals(1, model.getColumnCount(), "Should have 1 column for unknown type");
        assertEquals("Data", model.getColumnName(0), "Default column should be 'Data'");
        assertNull(model.getValueAt(0, 0), "Unknown type should return null values");
    }

    // Helper method to access package-private field for testing
    private String[] getColumns() {
        return model.columns;
    }
}