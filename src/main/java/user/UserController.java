package user;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import core.ApplicationException;
import core.Mapper;
import user.dto.UserDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UserController implements HttpHandler {
    private static UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        if (!path.startsWith("/user")) {
            return;
        }
        String response = "";
        if (path.startsWith("/user/create") && "POST".equals(method)) {
            InputStream is = exchange.getRequestBody();

            User user = Mapper.INSTANCE.readValue(is, User.class);
            UserDto userCreated = createUser(user);
            response = Mapper.INSTANCE.writeValueAsString(userCreated);
        }

        if (path.startsWith("/user") && "GET".equals(method)) {
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = Arrays.stream(query.split("&"))
                    .map(param -> param.split("="))
                    .collect(Collectors.toMap(
                            arr -> arr[0],
                            arr -> arr[1]
                    ));

            Long userId = Long.parseLong(params.get("id"));
            UserDto user = findById(userId);
            response = Mapper.INSTANCE.writeValueAsString(user);
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

    private UserDto createUser(User user) {
        return userServiceImpl.create(user);
    }

    private UserDto findById(Long userId) {
         return userServiceImpl.findById(userId)
                 .orElseThrow(() -> new ApplicationException(100, "User not found"));
    }
}
