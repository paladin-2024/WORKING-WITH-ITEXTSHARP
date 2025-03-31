package database.DAO;

import database.DBConnection;
import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getDate("join_date")
                ));
            }
        }
        return employees;
    }

    public boolean addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (name, email, department, salary, join_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setDouble(4, employee.getSalary());
            pstmt.setDate(5, new java.sql.Date(employee.getJoinDate().getTime()));
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employees SET name=?, email=?, department=?, salary=?, join_date=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setDouble(4, employee.getSalary());
            pstmt.setDate(5, new java.sql.Date(employee.getJoinDate().getTime()));
            pstmt.setInt(6, employee.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployee(int id) throws SQLException {
        String query = "DELETE FROM employees WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}