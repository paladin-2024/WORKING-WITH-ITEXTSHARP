package database;

import model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<Department> getAllDepartments() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM departments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Department dept = mapResultSetToDepartment(rs);
                departments.add(dept);
            }
        }
        return departments;
    }

    public boolean addDepartment(Department department) throws SQLException {
        String query = "INSERT INTO departments (name, location, budget) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            setDepartmentParameters(pstmt, department);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        department.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public boolean updateDepartment(Department department) throws SQLException {
        String query = "UPDATE departments SET name = ?, location = ?, budget = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            setDepartmentParameters(pstmt, department);
            pstmt.setInt(4, department.getId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDepartment(int id) throws SQLException {
        String query = "DELETE FROM departments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Department getDepartmentById(int id) throws SQLException {
        String query = "SELECT * FROM departments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDepartment(rs);
                }
            }
        }
        return null;
    }

    // Helper methods
    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        Department dept = new Department();
        dept.setId(rs.getInt("id"));
        dept.setName(rs.getString("name"));
        dept.setLocation(rs.getString("location"));
        dept.setBudget(rs.getDouble("budget"));
        return dept;
    }

    private void setDepartmentParameters(PreparedStatement pstmt, Department department) throws SQLException {
        pstmt.setString(1, department.getName());
        pstmt.setString(2, department.getLocation());
        pstmt.setDouble(3, department.getBudget());
    }
}