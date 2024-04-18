package com.example.demo.utilities;

import com.example.demo.record.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtilities {
    private static final DatabaseConfig DATABASE_CONFIG = DatabaseConfig.createWithDefaults();
    private DatabaseUtilities() {
    }
    static {
        try {
            Class.forName( DATABASE_CONFIG.MYSQL_JDBC_DRIVER_CLASS());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE_CONFIG.jdbcUrl(),
                DATABASE_CONFIG.username(),
                DATABASE_CONFIG.password()
        );
    }
    private static PreparedStatement prepareStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }
    public static String signUpMethod(String username, String password) {
        String query = "INSERT INTO tbluseraccount (user_name, password_hashed) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowAffected = preparedStatement.executeUpdate();

            return (rowAffected > 0) ? "Data inserted successfully!" : "Failed to insert data.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to insert data.";
        }
    }
    // Method to check user credentials in the database
    public static Boolean userCheckerMethod(String username, String password) {
        String query = "SELECT * FROM " + DATABASE_CONFIG.tableName() + " WHERE user_name = ? AND password_hashed = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            return preparedStatement.executeQuery().isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
