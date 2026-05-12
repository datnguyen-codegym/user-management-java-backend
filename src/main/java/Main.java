import com.sun.net.httpserver.HttpServer;
import config.Database;
import org.h2.tools.Server;
import user.UserController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        initServer();
        Database.init();
        System.out.println("Server started at http://localhost:8080");
    }

    private static void initServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/user", new UserController());
        server.setExecutor(null);
        server.start();
    }
}