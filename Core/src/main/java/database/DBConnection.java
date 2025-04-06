package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection testConnection;

    public static Connection getConnection() throws SQLException {
        if (testConnection != null) {
            return testConnection;
        }
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_management",
                "root",
                "2006"
        );
    }

    public static void setTestConnection(Connection connection) {
        testConnection = connection;
    }

    public static void clearTestConnection() {
        testConnection = null;
    }
}