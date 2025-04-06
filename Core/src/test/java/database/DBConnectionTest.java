package database;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {


    @org.junit.jupiter.api.Test
    void testCloseConnection() {
        // Test closing the connection
        DBConnection dbConnection = new DBConnection();
        assertThrows(SQLException.class, () -> dbConnection.getConnection().createStatement());
    }
}