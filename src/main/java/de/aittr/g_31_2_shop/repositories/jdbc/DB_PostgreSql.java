package de.aittr.g_31_2_shop.repositories.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_PostgreSql {
    private static final String DB_DRIVER_PATH = "org.postgresql.Driver";
    private static final String DB_ADDRESS = "jdbc:postgresql://localhost:5432/";
    private static final String DB_NAME = "31_2_shop";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwerty007";

    public static Connection getConnection() {
        try {
            Class.forName(DB_DRIVER_PATH); // do not forget to put dependency (mysql-connector-java) in pom.xml
            String dbUrl = String.format("%s%s?user=%s&password=%s", DB_ADDRESS, DB_NAME, DB_USERNAME, DB_PASSWORD);
            // ? - указывает на то, что после пойдут параметры
            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
