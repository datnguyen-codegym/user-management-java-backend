package user;

import core.Repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserRepo implements Repo<User> {
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
