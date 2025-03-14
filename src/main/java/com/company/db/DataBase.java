package com.company.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("postgresql://postgres:wLlRErexWNOSqgtfxCrjQHFnmOhHIICw@interchange.proxy.rlwy.net:54231/railway", "user", "1702");
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public static void initTable() {
        String profile = """
                create table if not exists profile (
                    id SERIAL PRIMARY KEY,
                    fullName VARCHAR(255),
                    chatId VARCHAR(30),
                    phone VARCHAR(20),
                    role VARCHAR(50)
                );
                """;
        execute(profile);
    }

    private static void execute(String sql) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
