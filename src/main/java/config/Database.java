package config;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static final String URL =
            "jdbc:h2:~/testdb";

    public static void init() throws SQLException {
        Server.createWebServer(
                "-web",
                "-webPort",
                "8082").start();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                "sa",
                ""
        );
    }
}
