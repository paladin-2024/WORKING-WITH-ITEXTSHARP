package database.DAO;

import database.DBConnection;
import model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public List<Department> getAllDepartments() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM departments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departments.add(new Department(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getDouble("budget")
                ));
            }
        }
        return departments;
    }

    public boolean addDepartment(Department department) throws SQLException {
        String query = "INSERT INTO departments (name, location, budget) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getLocation());
            pstmt.setDouble(3, department.getBudget());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateDepartment(Department department) throws SQLException {
        String query = "UPDATE departments SET name=?, location=?, budget=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getLocation());
            pstmt.setDouble(3, department.getBudget());
            pstmt.setInt(4, department.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDepartment(int id) throws SQLException {
        String query = "DELETE FROM departments WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}