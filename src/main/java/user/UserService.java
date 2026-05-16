package user;

import user.dto.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Collection<UserDto> list();

    Optional<UserDto> findById(Long id);

    void delete(Long id);

    UserDto update(User obj);

    UserDto create(User obj);
}
