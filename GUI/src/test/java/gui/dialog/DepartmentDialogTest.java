package gui.dialog;

import model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDialogTest {
    private JFrame parentFrame;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        parentFrame = new JFrame();
        testDepartment = new Department(1, "Engineering", "Building A", 100000.0);
    }

    @Test
    void testDialogInitializationForAdd() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);

        assertNotNull(dialog, "Dialog should be initialized");
        assertEquals("Add Department", dialog.dialog.getTitle(), "Title should be 'Add Department'");
        assertTrue(dialog.dialog.isModal(), "Dialog should be modal");
        assertFalse(dialog.confirmed, "Confirmed flag should be false initially");
    }

    @Test
    void testDialogInitializationForEdit() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame, testDepartment);

        assertEquals("Edit Department", dialog.dialog.getTitle(), "Title should be 'Edit Department'");
        assertEquals("Engineering", dialog.nameField.getText(), "Name field should match department");
        assertEquals("Building A", dialog.locationField.getText(), "Location field should match department");
        assertEquals("100000.0", dialog.budgetField.getText(), "Budget field should match department");
    }

    @Test
    void testFieldInitializationForNewDepartment() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);

        assertEquals("", dialog.nameField.getText(), "Name field should be empty");
        assertEquals("", dialog.locationField.getText(), "Location field should be empty");
        assertEquals("", dialog.budgetField.getText(), "Budget field should be empty");
    }

    @Test
    void testValidateInputSuccess() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        dialog.nameField.setText("Marketing");
        dialog.locationField.setText("Building B");
        dialog.budgetField.setText("75000.50");

        assertTrue(dialog.validateInput(), "Validation should pass with valid input");
    }

    @Test
    void testValidateInputMissingName() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        dialog.nameField.setText("");
        dialog.locationField.setText("Building B");
        dialog.budgetField.setText("75000");

        assertFalse(dialog.validateInput(), "Validation should fail with empty name");
    }

    @Test
    void testValidateInputInvalidBudget() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        dialog.nameField.setText("Marketing");
        dialog.locationField.setText("Building B");
        dialog.budgetField.setText("not-a-number");

        assertFalse(dialog.validateInput(), "Validation should fail with invalid budget");
    }

    @Test
    void testUpdateDepartmentFromFields() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        dialog.nameField.setText("HR");
        dialog.locationField.setText("Building C");
        dialog.budgetField.setText("50000.75");

        dialog.updateDepartmentFromFields();
        Department updatedDept = dialog.getDepartment();

        assertEquals("HR", updatedDept.getName(), "Department name should be updated");
        assertEquals("Building C", updatedDept.getLocation(), "Department location should be updated");
        assertEquals(50000.75, updatedDept.getBudget(), "Department budget should be updated");
    }

    @Test
    void testHandleSaveWithValidInput() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        dialog.nameField.setText("Finance");
        dialog.locationField.setText("Building D");
        dialog.budgetField.setText("80000");

        dialog.handleSave(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));

        assertTrue(dialog.confirmed, "Confirmed flag should be true after valid save");
        assertEquals("Finance", dialog.getDepartment().getName(), "Department should be updated");
    }

    @Test
    void testHandleSaveWithInvalidInput() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        dialog.nameField.setText("");
        dialog.locationField.setText("Building D");
        dialog.budgetField.setText("80000");

        dialog.handleSave(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));

        assertFalse(dialog.confirmed, "Confirmed flag should remain false after invalid save");
    }

    @Test
    void testShowDialogConfirmed() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);

        // Simulate UI interaction
        SwingUtilities.invokeLater(() -> {
            dialog.nameField.setText("Test Dept");
            dialog.locationField.setText("Test Building");
            dialog.budgetField.setText("10000");

            // Programmatically click the save button
            for (Component c : dialog.dialog.getContentPane().getComponents()) {
                if (c instanceof JPanel) {
                    for (Component button : ((JPanel)c).getComponents()) {
                        if (button instanceof JButton && "Save".equals(((JButton)button).getText())) {
                            ((JButton)button).doClick();
                            break;
                        }
                    }
                }
            }
        });

        assertTrue(dialog.showDialog(), "showDialog should return true when confirmed");
    }

    @Test
    void testShowDialogCancelled() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);

        // Simulate UI interaction
        SwingUtilities.invokeLater(() -> {
            // Programmatically click the cancel button
            for (Component c : dialog.dialog.getContentPane().getComponents()) {
                if (c instanceof JPanel) {
                    for (Component button : ((JPanel)c).getComponents()) {
                        if (button instanceof JButton && "Cancel".equals(((JButton)button).getText())) {
                            ((JButton)button).doClick();
                            break;
                        }
                    }
                }
            }
        });

        assertFalse(dialog.showDialog(), "showDialog should return false when cancelled");
    }

    @Test
    void testBudgetFieldAlignment() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame);
        assertEquals(JTextField.RIGHT, dialog.budgetField.getHorizontalAlignment(),
                "Budget field should be right-aligned");
    }

    @Test
    void testGetDepartment() {
        DepartmentDialog dialog = new DepartmentDialog(parentFrame, testDepartment);
        Department result = dialog.getDepartment();

        assertEquals(testDepartment.getId(), result.getId(), "Department ID should match");
        assertEquals(testDepartment.getName(), result.getName(), "Department name should match");
        assertEquals(testDepartment.getLocation(), result.getLocation(), "Department location should match");
        assertEquals(testDepartment.getBudget(), result.getBudget(), "Department budget should match");
    }
}