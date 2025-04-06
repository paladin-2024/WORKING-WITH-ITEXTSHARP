package model;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @org.junit.jupiter.api.Test
    void testEmployeeDefaultConstructor() {
        // Test the default constructor
        Employee employee = new Employee();
        assertEquals(0, employee.getId());
        assertNull(employee.getName());
        assertNull(employee.getDepartment());
        assertEquals(0, employee.getSalary(), 0.01);
    }

    @org.junit.jupiter.api.Test
    void testSettersAndGetters() {
        // Test setters and getters
        Employee employee = new Employee();
        employee.setId(2);
        employee.setName("Jane Smith");
        employee.setDepartment("HR");
        employee.setSalary(60000);

        assertEquals(2, employee.getId());
        assertEquals("Jane Smith", employee.getName());
        assertEquals("HR", employee.getDepartment());
        assertEquals(60000, employee.getSalary(), 0.01);
    }
}