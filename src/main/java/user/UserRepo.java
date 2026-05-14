package user;

import config.Database;
import core.Repo;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserRepo implements Repo<User> {
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
        User x = new User();
//        x.get
        return List.of();
    }

    @Override
    public Optional<User> findById() {
        return Optional.empty();
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
                "INSERT INTO users(id, full_name, year_of_birth, user_name, email, password) " +
                        " VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement ps =
                         connection.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {
                ps.setLong(1, obj.getId());
                ps.setString(2, obj.getFullName());
                ps.setInt(3, obj.getYearOfBirth());
                ps.setString(4, obj.getUsername());
                ps.setString(5, obj.getEmail());
                ps.setString(6, obj.getPassword());

                ps.executeQuery();

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
