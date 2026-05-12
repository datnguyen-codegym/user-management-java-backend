package config;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL =
            "jdbc:h2:~/testdb";

    private static Connection connection;

    public static void init() throws SQLException {

        connection = DriverManager.getConnection(
                URL,
                "sa",
                ""
        );

        Server.createWebServer(
                "-web",
                "-webPort",
                "8082").start();
    }

    public static Connection getConnection() {
        return connection;
    }
}
