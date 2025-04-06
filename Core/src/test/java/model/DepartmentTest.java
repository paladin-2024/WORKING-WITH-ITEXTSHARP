package model;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    @org.junit.jupiter.api.Test
    void testDepartmentConstructor() {
        // Test the constructor with parameters
        Department department = new Department(1, "IT", "Building A", 100000);
        assertEquals(1, department.getId());
        assertEquals("IT", department.getName());
        assertEquals("Building A", department.getLocation());
        assertEquals(100000, department.getBudget(), 0.01);
    }

    @org.junit.jupiter.api.Test
    void testDepartmentDefaultConstructor() {
        // Test the default constructor
        Department department = new Department();
        assertEquals(0, department.getId());
        assertNull(department.getName());
        assertNull(department.getLocation());
        assertEquals(0, department.getBudget(), 0.01);
    }

    @org.junit.jupiter.api.Test
    void testSettersAndGetters() {
        // Test setters and getters
        Department department = new Department();
        department.setId(2);
        department.setName("HR");
        department.setLocation("Building B");
        department.setBudget(50000);

        assertEquals(2, department.getId());
        assertEquals("HR", department.getName());
        assertEquals("Building B", department.getLocation());
        assertEquals(50000, department.getBudget(), 0.01);
    }
}