package com.example.demo.record;

public record DatabaseConfig(
        String jdbcUrl,
        String username,
        String password,
        String tableName,
        String MYSQL_JDBC_DRIVER_CLASS
){
    private DatabaseConfig() {
        this(
                "jdbc:mysql://localhost:3306/dbjavafx",
                "root",
                "Chua123",
                "tbluseraccount",
                "com.mysql.cj.jdbc.Driver"
        );
    }
    public static DatabaseConfig createWithDefaults() {
        return new DatabaseConfig();
    }
}
