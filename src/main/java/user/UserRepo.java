package user;

import config.Database;
import core.Repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserRepo implements Repo<User, Long> {
    private UserRepo(){}
    private static UserRepo INSTANCE;
    public static synchronized UserRepo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepo();
        }
        return INSTANCE;
    }
    @Override
    public Collection<User> list() {

        String sql = "SELECT * FROM users";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (rs.next()) {

                User user = new User();

                user.setId(rs.getLong("id"));
                user.setFullName(rs.getString("full_name"));
                user.setYearOfBirth(rs.getInt("year_of_birth"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));

                users.add(user);
            }

            return users;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getLong("id"));
                user.setFullName(rs.getString("full_name"));
                user.setYearOfBirth(rs.getInt("year_of_birth"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                return Optional.of(user);
            }

            return Optional.empty();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User update(User obj) {
        return null;
    }

    @Override
    public User create(User obj) {
        String sql =
                "INSERT INTO users(full_name, year_of_birth, username, email, password) " +
                        " VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = Database.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement ps =
                         connection.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {
                ps.setString(1, obj.getFullName());
                ps.setInt(2, obj.getYearOfBirth());
                ps.setString(3, obj.getUsername());
                ps.setString(4, obj.getEmail());
                ps.setString(5, obj.getPassword());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                }

                connection.commit();

                return obj;

            } catch (Exception e) {

                connection.rollback();

                throw e;
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
