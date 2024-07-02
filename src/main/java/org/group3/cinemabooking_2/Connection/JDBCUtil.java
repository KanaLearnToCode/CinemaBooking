package org.group3.cinemabooking_2.Connection;


import java.sql.*;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CinemaBooking;user=sa;password=123;encrypt=true;trustServerCertificate=true";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connect) {
        try {
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
