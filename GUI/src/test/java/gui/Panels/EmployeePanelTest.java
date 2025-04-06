package gui.Panels;

import database.DAO.EmployeeDAO;
import gui.TableModel.EmployeeTableModel;
import gui.dialog.EmployeeDialog;
import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePanelTest {
    private EmployeePanel employeePanel;
    private TestEmployeeDAO testDAO;
    private EmployeeTableModelSpy tableModelSpy;
    private JFrame parentFrame;

    @BeforeEach
    void setUp() {
        parentFrame = new JFrame();
        testDAO = new TestEmployeeDAO();
        tableModelSpy = new EmployeeTableModelSpy();
        employeePanel = new EmployeePanel();

        // Replace components with test versions
        setPrivateField(employeePanel, "tableModel", tableModelSpy);
        setPrivateField(employeePanel, "employeeDAO", testDAO);
    }

    @Test
    void testInitialization() {
        JPanel panel = employeePanel.getPanel();
        assertNotNull(panel, "Panel should be initialized");
        assertEquals(BorderLayout.class, panel.getLayout().getClass(), "Should use BorderLayout");

        // Verify components exist
        assertNotNull(getPrivateField(employeePanel, "employeeTable"), "Table should exist");
        assertNotNull(getPrivateField(employeePanel, "loadDataBtn"), "Load button should exist");
        assertNotNull(getPrivateField(employeePanel, "addEmployeeBtn"), "Add button should exist");
        assertNotNull(getPrivateField(employeePanel, "editEmployeeBtn"), "Edit button should exist");
        assertNotNull(getPrivateField(employeePanel, "deleteEmployeeBtn"), "Delete button should exist");
    }

    @Test
    void testLoadDataSuccess() throws SQLException {
        // Setup test data
        List<Employee> testData = new ArrayList<>();
        testData.add(new Employee(1, "John Doe", "john@example.com",
                "Engineering", 75000.0,
                new java.sql.Date(System.currentTimeMillis())));
        testDAO.setTestData(testData);

        invokePrivateMethod(employeePanel, "loadData");

        assertTrue(tableModelSpy.wasFireTableDataChangedCalled(), "Should refresh table");
        assertEquals(1, tableModelSpy.getEmployees().size(), "Should load one employee");
        assertEquals("John Doe", tableModelSpy.getEmployees().get(0).getName(), "Employee name should match");
    }

    @Test
    void testLoadDataFailure() throws SQLException {
        testDAO.setShouldThrowException(true);

        // Test that no exception is thrown to the caller
        assertDoesNotThrow(() -> invokePrivateMethod(employeePanel, "loadData"));

        // Verify error handling
        assertFalse(tableModelSpy.wasFireTableDataChangedCalled(), "Should not refresh on error");
    }

    @Test
    void testAddEmployeeSuccess() throws SQLException {
        // Create test dialog that confirms
        EmployeeDialogSpy dialogSpy = new EmployeeDialogSpy(parentFrame, true);
        setPrivateField(employeePanel, "employeeDialog", dialogSpy);

        invokePrivateMethod(employeePanel, "addEmployee");

        assertTrue(testDAO.wasAddCalled(), "DAO add should be called");
        assertTrue(tableModelSpy.wasFireTableDataChangedCalled(), "Should refresh after add");
    }

    @Test
    void testAddEmployeeFailure() throws SQLException {
        // Create test dialog that confirms
        EmployeeDialogSpy dialogSpy = new EmployeeDialogSpy(parentFrame, true);
        setPrivateField(employeePanel, "employeeDialog", dialogSpy);

        testDAO.setShouldThrowException(true);

        invokePrivateMethod(employeePanel, "addEmployee");

        assertTrue(testDAO.wasAddCalled(), "DAO add should be called");
        assertFalse(tableModelSpy.wasFireTableDataChangedCalled(), "Should not refresh on error");
    }

    @Test
    void testAddEmployeeCancel() {
        // Create test dialog that cancels
        EmployeeDialogSpy dialogSpy = new EmployeeDialogSpy(parentFrame, false);
        setPrivateField(employeePanel, "employeeDialog", dialogSpy);

        invokePrivateMethod(employeePanel, "addEmployee");

        assertFalse(testDAO.wasAddCalled(), "DAO add should not be called");
        assertFalse(tableModelSpy.wasFireTableDataChangedCalled(), "Should not refresh on cancel");
    }

    @Test
    void testEditEmployeeSuccess() throws SQLException {
        // Setup test data
        List<Employee> testData = new ArrayList<>();
        testData.add(new Employee(1, "John Doe", "john@example.com",
                "Engineering", 75000.0,
                new java.sql.Date(System.currentTimeMillis())));
        tableModelSpy.setEmployees(testData);

        // Set table selection to first row
        JTable tableSpy = new JTable();
        tableSpy.setRowSelectionInterval(0, 0);
        setPrivateField(employeePanel, "employeeTable", tableSpy);

        // Create test dialog that confirms
        EmployeeDialogSpy dialogSpy = new EmployeeDialogSpy(parentFrame, true);
        setPrivateField(employeePanel, "employeeDialog", dialogSpy);

        invokePrivateMethod(employeePanel, "editEmployee");

        assertTrue(testDAO.wasUpdateCalled(), "DAO update should be called");
        assertTrue(tableModelSpy.wasFireTableDataChangedCalled(), "Should refresh after edit");
    }

    @Test
    void testEditEmployeeNoSelection() {
        // Set table selection to -1 (no selection)
        JTable tableSpy = new JTable();
        tableSpy.setRowSelectionInterval(-1, -1);
        setPrivateField(employeePanel, "employeeTable", tableSpy);

        invokePrivateMethod(employeePanel, "editEmployee");

        assertFalse(testDAO.wasUpdateCalled(), "DAO update should not be called");
        assertFalse(tableModelSpy.wasFireTableDataChangedCalled(), "Should not refresh without selection");
    }

    @Test
    void testDeleteEmployeeSuccess() throws SQLException {
        // Setup test data
        List<Employee> testData = new ArrayList<>();
        testData.add(new Employee(1, "John Doe", "john@example.com",
                "Engineering", 75000.0,
                new java.sql.Date(System.currentTimeMillis())));
        tableModelSpy.setEmployees(testData);

        // Set table selection to first row
        JTable tableSpy = new JTable();
        tableSpy.setRowSelectionInterval(0, 0);
        setPrivateField(employeePanel, "employeeTable", tableSpy);

        // Simulate user confirming deletion
        setPrivateField(employeePanel, "confirmDelete", true);

        invokePrivateMethod(employeePanel, "deleteEmployee");

        assertTrue(testDAO.wasDeleteCalled(), "DAO delete should be called");
        assertTrue(tableModelSpy.wasFireTableDataChangedCalled(), "Should refresh after delete");
    }

    @Test
    void testDeleteEmployeeCancel() throws SQLException {
        // Setup test data
        List<Employee> testData = new ArrayList<>();
        testData.add(new Employee(1, "John Doe", "john@example.com",
                "Engineering", 75000.0,
                new java.sql.Date(System.currentTimeMillis())));
        tableModelSpy.setEmployees(testData);

        // Set table selection to first row
        JTable tableSpy = new JTable();
        tableSpy.setRowSelectionInterval(0, 0);
        setPrivateField(employeePanel, "employeeTable", tableSpy);

        // Simulate user canceling deletion
        setPrivateField(employeePanel, "confirmDelete", false);

        invokePrivateMethod(employeePanel, "deleteEmployee");

        assertFalse(testDAO.wasDeleteCalled(), "DAO delete should not be called");
        assertFalse(tableModelSpy.wasFireTableDataChangedCalled(), "Should not refresh on cancel");
    }

    // Helper methods to access private members via reflection
    private void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            fail("Failed to set field: " + e.getMessage());
        }
    }

    private Object getPrivateField(Object obj, String fieldName) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            fail("Failed to get field: " + e.getMessage());
            return null;
        }
    }

    private void invokePrivateMethod(Object obj, String methodName) {
        try {
            var method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(obj);
        } catch (Exception e) {
            fail("Failed to invoke method: " + e.getMessage());
        }
    }

    // Test double for EmployeeDAO
    static class TestEmployeeDAO extends EmployeeDAO {
        private List<Employee> testData = new ArrayList<>();
        private boolean shouldThrowException = false;
        private boolean addCalled = false;
        private boolean updateCalled = false;
        private boolean deleteCalled = false;

        void setTestData(List<Employee> data) {
            this.testData = data;
        }

        void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        boolean wasAddCalled() {
            return addCalled;
        }

        boolean wasUpdateCalled() {
            return updateCalled;
        }

        boolean wasDeleteCalled() {
            return deleteCalled;
        }

        @Override
        public List<Employee> getAllEmployees() throws SQLException {
            if (shouldThrowException) {
                throw new SQLException("Test exception");
            }
            return testData;
        }

        @Override
        public boolean addEmployee(Employee employee) throws SQLException {
            addCalled = true;
            return !shouldThrowException;
        }

        @Override
        public boolean updateEmployee(Employee employee) throws SQLException {
            updateCalled = true;
            return !shouldThrowException;
        }

        @Override
        public boolean deleteEmployee(int id) throws SQLException {
            deleteCalled = true;
            return !shouldThrowException;
        }
    }

    // Spy for EmployeeTableModel
    static class EmployeeTableModelSpy extends EmployeeTableModel {
        private boolean fireTableDataChangedCalled = false;

        @Override
        public void fireTableDataChanged() {
            fireTableDataChangedCalled = true;
            super.fireTableDataChanged();
        }

        boolean wasFireTableDataChangedCalled() {
            return fireTableDataChangedCalled;
        }

        public List<Employee> getEmployees() {
            return super.employees;
        }
    }

    // Test double for EmployeeDialog
    static class EmployeeDialogSpy extends EmployeeDialog {
        private final boolean confirmValue;

        public EmployeeDialogSpy(JFrame parent, boolean confirmValue) {
            super(parent);
            this.confirmValue = confirmValue;
        }

        @Override
        public boolean showDialog() {
            return confirmValue;
        }
    }
}