package database.DAO;
import database.DBConnection;
import model.Report;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    public List<Report> generateSalaryReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT department, COUNT(*) AS employee_count, "
                + "SUM(salary) AS total_salary, AVG(salary) AS avg_salary, "
                + "MIN(salary) AS min_salary, MAX(salary) AS max_salary "
                + "FROM employees GROUP BY department";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                reports.add(Report.createSalaryReport(
                        0,
                        LocalDate.now(),
                        rs.getInt("employee_count"),
                        rs.getDouble("total_salary"),
                        rs.getString("department"),
                        rs.getDouble("avg_salary"),
                        rs.getDouble("min_salary"),
                        rs.getDouble("max_salary")
                ));
            }
        }
        return reports;
    }

    public List<Report> generateNewHiresReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT id, name, email, department, salary, join_date "
                + "FROM employees WHERE join_date >= CURDATE() - INTERVAL 30 DAY";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                reports.add(Report.createNewHireReport(
                        rs.getInt("id"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getDouble("salary"),
                        rs.getString("department"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        }
        return reports;
    }

    public List<Report> generateDepartmentAnalysis() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT d.name AS department, COUNT(e.id) AS employee_count, "
                + "SUM(e.salary) AS total_salary, d.budget, "
                + "ROUND((SUM(e.salary)/d.budget)*100, 2) AS utilization "
                + "FROM departments d LEFT JOIN employees e ON d.name = e.department "
                + "GROUP BY d.name, d.budget";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                reports.add(Report.createDepartmentAnalysis(
                        LocalDate.now(),
                        rs.getInt("employee_count"),
                        rs.getDouble("total_salary"),
                        rs.getString("department"),
                        rs.getDouble("budget"),
                        rs.getDouble("utilization")
                ));
            }
        }
        return reports;
    }

    public List<Report> generateMonthlyReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT DATE_FORMAT(join_date, '%Y-%m') AS month, department, "
                + "COUNT(*) AS employee_count, SUM(salary) AS total_salary "
                + "FROM employees GROUP BY month, department";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                reports.add(Report.createMonthlyReport(
                        LocalDate.parse(rs.getString("month") + "-01"),
                        rs.getInt("employee_count"),
                        rs.getDouble("total_salary"),
                        rs.getString("department")
                ));
            }
        }
        return reports;
    }
}