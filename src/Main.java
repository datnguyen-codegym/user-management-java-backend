import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // API endpoint
        server.createContext("/user", new UserController());

        // Thread pool xử lý request
        server.setExecutor(null);

        // Start server
        server.start();

        System.out.println("Server started at http://localhost:8080");


    }
}