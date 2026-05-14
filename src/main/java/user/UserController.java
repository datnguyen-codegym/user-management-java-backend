package user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UserController implements HttpHandler {
    private static UserService userService = UserService.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        if (!path.startsWith("/user")) {
            return;
        }
        String response = "";
        if (path.startsWith("/user/create") && "POST".equalsIgnoreCase(method)) {
            InputStream is = exchange.getRequestBody();
            ObjectMapper mapper = new ObjectMapper();

            User user = mapper.readValue(is, User.class);
            user = createUser(user);
            response = mapper.writeValueAsString(user);
        }

//        response = this.getUsers();


        // ===== CORS =====
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Safari hay gửi OPTIONS preflight
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        // HTTP status code
        exchange.sendResponseHeaders(200, response.getBytes().length);

        // response body
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getUsers() {
        return "list users";
    }

    private User createUser(User user) {
        return userService.create(user);
    }
}
