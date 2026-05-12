package user;

import core.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserService implements Service<User> {
    private UserService(){}
    private static UserService INSTANCE;
    public static synchronized UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    @Override
    public Collection<User> list() {
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
        return null;
    }
}
