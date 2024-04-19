package com.example.demo.utilities;

import com.example.demo.record.DatabaseConfig;

import java.sql.*;

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
    public static String insertDataToTable(String username, String password,
                                           String firstname, String lastname, String gender,
                                           String email, String student_id, String prog_languages) {
        String queryOne = "INSERT INTO `dbjavacrud`." + DATABASE_CONFIG.tableName()[0] +" (username, password) VALUES (?, ?)";
        String queryTwo = "INSERT INTO `dbjavacrud`." + DATABASE_CONFIG.tableName()[1] +" (" +
                "firstname, lastname, gender, email, student_id, prog_languages) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement preparedStatementTwo = prepareStatement(queryTwo);
                PreparedStatement preparedStatementOne = prepareStatement(queryOne);
            ) {



            preparedStatementTwo.setString(1, firstname);
            preparedStatementTwo.setString(2, lastname);
            preparedStatementTwo.setString(3, gender);
            preparedStatementTwo.setString(4, email);
            preparedStatementTwo.setString(5, student_id);
            preparedStatementTwo.setString(6, prog_languages);

            preparedStatementOne.setString(1, username);
            preparedStatementOne.setString(2, password);

            int rowAffectedTwo = preparedStatementOne.executeUpdate();
            int rowAffectedOne = preparedStatementOne.executeUpdate();

            return (rowAffectedOne > 0 && rowAffectedTwo > 0) ? "Data inserted successfully!" : "Failed to insert data.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to insert data.";
        }
    }
    // Method to check user credentials in the database
    public static Boolean checkIfDataExistInTable(String username, String password) {
        String query = "SELECT * FROM " + DATABASE_CONFIG.tableName()[0] + " WHERE user_name = ? AND password_hashed = ? RETURNING *";
        try (PreparedStatement preparedStatement = prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            return preparedStatement.executeQuery().isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Boolean deleteDataFromTable(String account_id ) {
        String query = "DELETE " +
                "FROM " + DATABASE_CONFIG.tableName()[0] + " " +
                "JOIN " + DATABASE_CONFIG.tableName()[1] + " ON " +
                DATABASE_CONFIG.tableName()[0] + ".account_id  = "+ DATABASE_CONFIG.tableName()[1] +".accountID" +
                "WHERE " + DATABASE_CONFIG.tableName()[0] + ".account_id  = ? OR"+ DATABASE_CONFIG.tableName()[1] +".accountID = ?" +
                "RETURNING *";
        try (
             PreparedStatement preparedStatement = prepareStatement(query)) {
            preparedStatement.setString(1, account_id);
            preparedStatement.setString(2, account_id);
            return preparedStatement.executeQuery().isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void createTable(){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){
            String queryOne = "CREATE TABLE IF NOT EXISTS `dbjavacrud`." + DATABASE_CONFIG.tableName()[0] + "(" +
                    "`account_id` INT NOT NULL AUTO_INCREMENT," +
                    "`username` VARCHAR(255) NOT NULL," +
                    "`password` VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (`account_id`)) ENGINE = InnoDB;";
            statement.executeUpdate(queryOne);
            String queryTwo = "CREATE TABLE IF NOT EXISTS `dbjavacrud`." + DATABASE_CONFIG.tableName()[1] + "(" +
                    "`profile_id` INT NOT NULL AUTO_INCREMENT," +
                    "`account_id` INT NOT NULL," +
                    "`firstname` VARCHAR(255) NOT NULL," +
                    "`lastname` VARCHAR(255) NOT NULL," +
                    "`gender` VARCHAR(10) NOT NULL," +
                    "`email` VARCHAR(255) NOT NULL," +
                    "`student_id` VARCHAR(255) NOT NULL," +
                    "`prog_languages` VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (`profile_id`)," +
                    "FOREIGN KEY (`account_id`) REFERENCES " + DATABASE_CONFIG.tableName()[0] + "(`account_id`)" +
            ") ENGINE = InnoDB;";
            statement.executeUpdate(queryTwo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        createTable();
        insertDataToTable("Momonan0412", "123",
                "Avril Nigel", "Chua", "Male", "asd@asd", "22-4869-876", "JAV");
        /*String username, String password, String firstname, String lastname, String gender, String email, String student_id, String prog_languages*/
    }
}
