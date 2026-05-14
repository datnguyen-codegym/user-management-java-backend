package config;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static final String URL =
            "jdbc:h2:~/testdb";

    public static void init() throws SQLException {
        Server.createWebServer(
                "-web",
                "-webPort",
                "8082").start();

        String sql =
        "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    full_name VARCHAR(255) NOT NULL,\n" +
                "    year_of_birth INT NOT NULL,\n" +
                "    username VARCHAR(100) NOT NULL UNIQUE,\n" +
                "    email VARCHAR(255) NOT NULL UNIQUE,\n" +
                "    password VARCHAR(255) NOT NULL\n" +
                ");"
    ;

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                "sa",
                ""
        );
    }
}
