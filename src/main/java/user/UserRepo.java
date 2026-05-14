package user;

import core.Repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

 class UserRepo implements Repo<User> {
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
        return null;
    }
}
