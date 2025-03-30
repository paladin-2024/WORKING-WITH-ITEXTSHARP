package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/employee_management";
        String user = "root";
        String password = "2006";
        return DriverManager.getConnection(url, user, password);
    }
}
