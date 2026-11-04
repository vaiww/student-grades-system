
package com.example.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static final Properties CONFIG = loadConfig();

    private static final String URL = CONFIG.getProperty("db.url");
    private static final String USER = CONFIG.getProperty("db.user");
    private static final String PASSWORD = CONFIG.getProperty("db.password");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить драйвер MySQL", e);
        }
    }

    private static Properties loadConfig() {
        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream(Path.of("db.properties"))) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Не удалось загрузить файл db.properties", e
            );
        }

        return properties;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
