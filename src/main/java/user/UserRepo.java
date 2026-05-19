package user;

import core.BaseRepo;

import java.sql.*;
import java.util.List;

public class UserRepo extends BaseRepo<User, Long> {

    private final static List<String> ORDER_INSERT_DB = List.of(
            "full_name",
            "year_of_birth",
            "username",
            "email",
            "password"
    );
    public UserRepo(String TABLE) {
        super(TABLE, ORDER_INSERT_DB);
    }

    private static UserRepo INSTANCE;
    public static synchronized UserRepo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepo("USERS");
        }
        return INSTANCE;
    }

    @Override
    protected User mappingFromDatabase(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFullName(rs.getString("full_name"));
        user.setYearOfBirth(rs.getInt("year_of_birth"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    @Override
    protected void mappingFromEntity(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getFullName());
        ps.setInt(2, entity.getYearOfBirth());
        ps.setString(3, entity.getUsername());
        ps.setString(4, entity.getEmail());
        ps.setString(5, entity.getPassword());
    }
}
