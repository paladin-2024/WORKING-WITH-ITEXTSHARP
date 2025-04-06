package gui.TableModel;

import model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTableModelTest {
    private DepartmentTableModel model;
    private Department testDept1;
    private Department testDept2;

    @BeforeEach
    void setUp() {
        model = new DepartmentTableModel();
        testDept1 = new Department(1, "Engineering", "Building A", 100000.0);
        testDept2 = new Department(2, "Marketing", "Building B", 75000.0);
    }

    @Test
    void testInitialState() {
        assertEquals(4, model.getColumnCount(), "Should have 4 columns");
        assertEquals(0, model.getRowCount(), "Initially should have 0 rows");
        assertEquals("ID", model.getColumnName(0), "First column should be ID");
        assertEquals("Name", model.getColumnName(1), "Second column should be Name");
        assertEquals("Location", model.getColumnName(2), "Third column should be Location");
        assertEquals("Budget", model.getColumnName(3), "Fourth column should be Budget");
    }

    @Test
    void testSetDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(testDept1);
        departments.add(testDept2);

        model.setDepartments(departments);

        assertEquals(2, model.getRowCount(), "Should have 2 rows after setting departments");
        assertEquals(testDept1, model.getDepartmentAt(0), "First department should match");
        assertEquals(testDept2, model.getDepartmentAt(1), "Second department should match");
    }

    @Test
    void testGetValueAt() {
        model.setDepartments(List.of(testDept1));

        assertEquals(1, model.getValueAt(0, 0), "ID should match");
        assertEquals("Engineering", model.getValueAt(0, 1), "Name should match");
        assertEquals("Building A", model.getValueAt(0, 2), "Location should match");
        assertEquals(100000.0, model.getValueAt(0, 3), "Budget should match");
    }

    @Test
    void testAddDepartment() {
        model.addDepartment(testDept1);

        assertEquals(1, model.getRowCount(), "Should have 1 row after add");
        assertEquals(testDept1, model.getDepartmentAt(0), "Added department should match");
    }

    @Test
    void testUpdateDepartment() {
        model.setDepartments(List.of(testDept1));
        Department updatedDept = new Department(1, "Updated Engineering", "Building C", 120000.0);

        model.updateDepartment(0, updatedDept);

        assertEquals("Updated Engineering", model.getValueAt(0, 1), "Name should be updated");
        assertEquals("Building C", model.getValueAt(0, 2), "Location should be updated");
        assertEquals(120000.0, model.getValueAt(0, 3), "Budget should be updated");
    }

    @Test
    void testRemoveDepartment() {
        model.setDepartments(List.of(testDept1, testDept2));

        model.removeDepartment(0);

        assertEquals(1, model.getRowCount(), "Should have 1 row after removal");
        assertEquals(testDept2, model.getDepartmentAt(0), "Remaining department should match");
    }

    @Test
    void testClearAll() {
        model.setDepartments(List.of(testDept1, testDept2));

        model.clearAll();

        assertEquals(0, model.getRowCount(), "Should have 0 rows after clear");
    }

    @Test
    void testIsCellEditable() {
        assertFalse(model.isCellEditable(0, 0), "ID column should not be editable");
        assertTrue(model.isCellEditable(0, 1), "Name column should be editable");
        assertTrue(model.isCellEditable(0, 2), "Location column should be editable");
        assertTrue(model.isCellEditable(0, 3), "Budget column should be editable");
    }

    @Test
    void testSetValueAt() {
        model.setDepartments(List.of(testDept1));

        // Test name update
        model.setValueAt("New Name", 0, 1);
        assertEquals("New Name", model.getValueAt(0, 1), "Name should be updated");

        // Test location update
        model.setValueAt("New Location", 0, 2);
        assertEquals("New Location", model.getValueAt(0, 2), "Location should be updated");

        // Test budget update
        model.setValueAt(150000.0, 0, 3);
        assertEquals(150000.0, model.getValueAt(0, 3), "Budget should be updated");
    }

    @Test
    void testSetValueAtWithInvalidData() {
        model.setDepartments(List.of(testDept1));

        // Test invalid budget (should throw NumberFormatException)
        assertThrows(NumberFormatException.class, () ->
                model.setValueAt("not-a-number", 0, 3));
    }

    @Test
    void testGetDepartmentAt() {
        model.setDepartments(List.of(testDept1, testDept2));

        assertEquals(testDept1, model.getDepartmentAt(0), "Should return correct department");
        assertEquals(testDept2, model.getDepartmentAt(1), "Should return correct department");
    }

    @Test
    void testGetDepartmentAtWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                model.getDepartmentAt(0), "Should throw for empty model");

        model.setDepartments(List.of(testDept1));
        assertThrows(IndexOutOfBoundsException.class, () ->
                model.getDepartmentAt(1), "Should throw for invalid index");
    }
}