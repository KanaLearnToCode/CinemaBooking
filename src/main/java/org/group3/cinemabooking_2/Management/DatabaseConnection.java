package org.group3.cinemabooking_2.Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CinemaBooking;user=sa;password=123;trustServerCertificate=true";
        return DriverManager.getConnection(url);
    }
}