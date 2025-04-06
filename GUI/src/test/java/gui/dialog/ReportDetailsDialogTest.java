package gui.dialog;

import model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReportDetailsDialogTest {
    private Report testReport;

    @BeforeEach
    void setUp() {
        testReport = Report.createSalaryReport(
                1,
                LocalDate.of(2023, 6, 15),
                10,
                500000.0,
                "Engineering",
                50000.0,
                30000.0,
                70000.0
        );
    }

    @Test
    void testDialogInitialization() {
        ReportDetailsDialog dialog = new ReportDetailsDialog(testReport);

        assertNotNull(dialog, "Dialog should be initialized");
        assertEquals("Report Details - Salary Report", dialog.getTitle(), "Dialog title should include report type");
        assertTrue(dialog.isModal(), "Dialog should be modal");
        assertEquals(400, dialog.getWidth(), "Dialog width should be 400");
        assertEquals(300, dialog.getHeight(), "Dialog height should be 300");
    }



    @Test
    void testReportDataDisplay() {
        ReportDetailsDialog dialog = new ReportDetailsDialog(testReport);
        JPanel panel = (JPanel) dialog.getContentPane().getComponents()[0];

        // Verify all labels and values are present
        assertEquals(10, panel.getComponentCount(), "Panel should have 10 components (5 rows)");

        // Check each label-value pair
        assertLabelValuePair(panel, 0, "Report ID:", "1");
        assertLabelValuePair(panel, 2, "Report Type:", "Salary Report");
        assertLabelValuePair(panel, 4, "Generated Date:", "2023-06-15");
        assertLabelValuePair(panel, 6, "Employee Count:", "10");
        assertLabelValuePair(panel, 8, "Total Salary:", "$500,000.00");
    }

    @Test
    void testCloseButtonFunctionality() {
        ReportDetailsDialog dialog = new ReportDetailsDialog(testReport);

        // Need to simulate button click in UI thread
        SwingUtilities.invokeLater(() -> {
            dialog.setVisible(true);
            for (Component c : dialog.getContentPane().getComponents()) {
                if (c instanceof JButton) {
                    ((JButton) c).doClick();
                    break;
                }
            }
        });

        // Give the UI time to process
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(dialog.isVisible(), "Dialog should be closed after clicking Close button");
    }

    @Test
    void testDifferentReportTypes() {
        // Test with different report types
        Report newHireReport = Report.createNewHireReport(
                2,
                LocalDate.of(2023, 6, 1),
                65000.0,
                "Marketing",
                "Jane Smith",
                "jane@example.com"
        );

        ReportDetailsDialog dialog = new ReportDetailsDialog(newHireReport);
        assertEquals("Report Details - New Hires Report", dialog.getTitle(),
                "Dialog title should reflect new report type");
    }

    private void assertLabelValuePair(JPanel panel, int startIndex, String expectedLabel, String expectedValue) {
        Component labelComp = panel.getComponent(startIndex);
        Component valueComp = panel.getComponent(startIndex + 1);

        assertTrue(labelComp instanceof JLabel, "Component at " + startIndex + " should be a JLabel");
        assertEquals(expectedLabel, ((JLabel) labelComp).getText(), "Label text mismatch");

        assertTrue(valueComp instanceof JLabel, "Component at " + (startIndex + 1) + " should be a JLabel");
        assertEquals(expectedValue, ((JLabel) valueComp).getText(), "Value text mismatch");
    }
}