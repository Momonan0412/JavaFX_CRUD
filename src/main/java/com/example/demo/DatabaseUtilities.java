package com.example.demo;

import com.example.demo.record.DatabaseConfig;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DatabaseUtilities {
    private static int primaryKeyOfTheLoggedInUser;
    private static String userNameOfTheLoggedInUser;
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
    public static void updateTableData(String username, String password) {
        String query = "UPDATE `dbjavacrud`." +
                DATABASE_CONFIG.tableName()[0] + " SET username = ?, password = ? WHERE account_id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            preparedStatement.setInt(3, DatabaseUtilities.getPrimaryKeyOfTheLoggedInUser());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String[] getDataFromTable() {
        String query = "SELECT * FROM `dbjavacrud`." + DATABASE_CONFIG.tableName()[1] + " WHERE account_id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)){
            preparedStatement.setInt(1, DatabaseUtilities.getPrimaryKeyOfTheLoggedInUser());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String student_id = resultSet.getString("student_id");
                String prog_languages = resultSet.getString("prog_languages");
                return new String[]{
                        firstname,
                        lastname,
                        gender,
                        email,
                        student_id,
                        prog_languages
                };
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String insertDataToTable(String username, String password,
                                           String firstname, String lastname, String gender,
                                           String email, String student_id, String prog_languages) {
        String queryOne = "INSERT INTO `dbjavacrud`." + DATABASE_CONFIG.tableName()[0] +" (username, password) VALUES (?, ?)";
        String queryTwo = "INSERT INTO `dbjavacrud`." + DATABASE_CONFIG.tableName()[1] +" (" +
                "account_id,firstname, lastname, gender, email, student_id, prog_languages) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (   Connection connection = getConnection();
                PreparedStatement preparedStatementOne = connection.prepareStatement(queryOne, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement preparedStatementTwo = prepareStatement(queryTwo);
            ) {
            preparedStatementOne.setString(1, username);
            preparedStatementOne.setString(2, password);
            int rowAffectedOne = preparedStatementOne.executeUpdate();
            ResultSet resultSet = preparedStatementOne.getGeneratedKeys();
            resultSet.next();
            preparedStatementTwo.setInt(1,  resultSet.getInt(1));
            preparedStatementTwo.setString(2, firstname);
            preparedStatementTwo.setString(3, lastname);
            preparedStatementTwo.setString(4, gender);
            preparedStatementTwo.setString(5, email);
            preparedStatementTwo.setString(6, student_id);
            preparedStatementTwo.setString(7, prog_languages);
            int rowAffectedTwo = preparedStatementTwo.executeUpdate();



            return (rowAffectedOne > 0 && rowAffectedTwo > 0) ? "Data inserted successfully!" : "Failed to insert data.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to insert data.";
        }
    }
    // Method to check user credentials in the database
    public static Boolean checkIfDataExistInTable(String username, String password) {
        String query = "SELECT account_id, password FROM " + DATABASE_CONFIG.tableName()[0] + " WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    setPrimaryKeyOfTheLoggedInUser(resultSet.getInt("account_id"));
                    setUserNameOfTheLoggedInUser(username);
                    String hashedPasswordFromDB = resultSet.getString("password");
                    return BCrypt.checkpw(password, hashedPasswordFromDB);
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void deleteCurrentLoggedInUser() {
        // DATABASE_CONFIG.tableName()[1] Contains foreign key that is "ON DELETE CASCADE"
        // Which deletes all with the Foreign key of "primaryKeyOfTheLoggedInUser"
        String query = "DELETE FROM " + DATABASE_CONFIG.tableName()[0] + " WHERE account_id = ?";
        try (PreparedStatement preparedStatement = prepareStatement(query)){
            preparedStatement.setInt(1, getPrimaryKeyOfTheLoggedInUser());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    "FOREIGN KEY (`account_id`) REFERENCES " + DATABASE_CONFIG.tableName()[0] + "(`account_id`) " +
                    "ON DELETE CASCADE" +
            ") ENGINE = InnoDB;";
            statement.executeUpdate(queryTwo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserNameOfTheLoggedInUser() {
        return userNameOfTheLoggedInUser;
    }

    public static void setUserNameOfTheLoggedInUser(String userNameOfTheLoggedInUser) {
        DatabaseUtilities.userNameOfTheLoggedInUser = userNameOfTheLoggedInUser;
    }

    public static int getPrimaryKeyOfTheLoggedInUser() {
        return primaryKeyOfTheLoggedInUser;
    }

    public static void setPrimaryKeyOfTheLoggedInUser(int primaryKeyOfTheLoggedInUser) {
        DatabaseUtilities.primaryKeyOfTheLoggedInUser = primaryKeyOfTheLoggedInUser;
    }

    public static void main(String[] args) {
        createTable();
    }
}
