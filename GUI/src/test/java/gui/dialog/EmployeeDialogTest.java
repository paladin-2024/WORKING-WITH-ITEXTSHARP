package gui.dialog;

import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDialogTest {
    private JFrame parentFrame;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        parentFrame = new JFrame();
        testEmployee = new Employee(1, "John Doe", "john@example.com",
                "Engineering", 75000.0, Date.valueOf("2023-01-15"));
    }

    @Test
    void testDialogCreationForAdd() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);
        assertNotNull(dialog, "Dialog should be created");
        assertEquals("Add Employee", dialog.getTitle(), "Title should be 'Add Employee'");
        assertFalse(dialog.isVisible(), "Dialog should not be visible initially");
    }

    @Test
    void testDialogCreationForEdit() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame, testEmployee);
        assertNotNull(dialog, "Dialog should be created");
        assertEquals("Edit Employee", dialog.getTitle(), "Title should be 'Edit Employee'");
        assertFalse(dialog.isVisible(), "Dialog should not be visible initially");
    }

    @Test
    void testInitialFieldValuesForAdd() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);

        // Verify default values for new employee
        assertEquals("", dialog.nameField.getText(), "Name field should be empty");
        assertEquals("", dialog.emailField.getText(), "Email field should be empty");
        assertEquals("", dialog.departmentField.getText(), "Department field should be empty");
        assertEquals("", dialog.salaryField.getText(), "Salary field should be empty");

        // Verify join date is set to today
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        assertEquals(today, dialog.joinDateField.getText(), "Join date should be today");
    }

    @Test
    void testInitialFieldValuesForEdit() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame, testEmployee);

        // Verify fields are populated with employee data
        assertEquals("John Doe", dialog.nameField.getText(), "Name field should match employee");
        assertEquals("john@example.com", dialog.emailField.getText(), "Email field should match employee");
        assertEquals("Engineering", dialog.departmentField.getText(), "Department field should match employee");
        assertEquals("75000.0", dialog.salaryField.getText(), "Salary field should match employee");
        assertEquals("2023-01-15", dialog.joinDateField.getText(), "Join date field should match employee");
    }

    @Test
    void testValidateInputSuccess() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);

        // Set valid values
        dialog.nameField.setText("Jane Smith");
        dialog.emailField.setText("jane@example.com");
        dialog.departmentField.setText("Marketing");
        dialog.salaryField.setText("65000.50");
        dialog.joinDateField.setText("2023-06-20");

        assertTrue(dialog.validateInput(), "Validation should pass with valid input");

        Employee result = dialog.getEmployee();
        assertEquals("Jane Smith", result.getName(), "Employee name should be updated");
        assertEquals("jane@example.com", result.getEmail(), "Employee email should be updated");
        assertEquals("Marketing", result.getDepartment(), "Employee department should be updated");
        assertEquals(65000.50, result.getSalary(), "Employee salary should be updated");
        assertEquals(Date.valueOf("2023-06-20"), result.getJoinDate(), "Employee join date should be updated");
    }

    @Test
    void testValidateInputInvalidSalary() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);

        // Set invalid salary
        dialog.nameField.setText("Jane Smith");
        dialog.emailField.setText("jane@example.com");
        dialog.departmentField.setText("Marketing");
        dialog.salaryField.setText("not-a-number");
        dialog.joinDateField.setText("2023-06-20");

        assertFalse(dialog.validateInput(), "Validation should fail with invalid salary");
    }

    @Test
    void testValidateInputInvalidDate() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);

        // Set invalid date
        dialog.nameField.setText("Jane Smith");
        dialog.emailField.setText("jane@example.com");
        dialog.departmentField.setText("Marketing");
        dialog.salaryField.setText("65000");
        dialog.joinDateField.setText("2023-13-45"); // Invalid date

        assertFalse(dialog.validateInput(), "Validation should fail with invalid date");
    }

    @Test
    void testShowDialogConfirmed() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);

        // Set valid values
        dialog.nameField.setText("Test Employee");
        dialog.emailField.setText("test@example.com");
        dialog.departmentField.setText("Test Dept");
        dialog.salaryField.setText("50000");
        dialog.joinDateField.setText("2023-01-01");

        // Need to simulate button click in UI thread
        SwingUtilities.invokeLater(() -> {
            dialog.setVisible(true);
            for (Component c : dialog.getContentPane().getComponents()) {
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

        assertTrue(dialog.showDialog(), "Dialog should return true when confirmed");
        assertTrue(dialog.confirmed, "Confirmed flag should be set");
    }

    @Test
    void testShowDialogCancelled() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame);

        // Need to simulate button click in UI thread
        SwingUtilities.invokeLater(() -> {
            dialog.setVisible(true);
            for (Component c : dialog.getContentPane().getComponents()) {
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

        assertFalse(dialog.showDialog(), "Dialog should return false when cancelled");
        assertFalse(dialog.confirmed, "Confirmed flag should not be set");
    }

    @Test
    void testGetEmployee() {
        EmployeeDialog dialog = new EmployeeDialog(parentFrame, testEmployee);
        Employee result = dialog.getEmployee();

        assertEquals(testEmployee.getId(), result.getId(), "Employee ID should match");
        assertEquals(testEmployee.getName(), result.getName(), "Employee name should match");
        assertEquals(testEmployee.getEmail(), result.getEmail(), "Employee email should match");
        assertEquals(testEmployee.getDepartment(), result.getDepartment(), "Employee department should match");
        assertEquals(testEmployee.getSalary(), result.getSalary(), "Employee salary should match");
        assertEquals(testEmployee.getJoinDate(), result.getJoinDate(), "Employee join date should match");
    }
}