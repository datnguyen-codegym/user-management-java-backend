package user;

import user.dto.UserDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserRepo repo = UserRepo.getInstance();

    private UserServiceImpl() {
    }

    private static UserServiceImpl INSTANCE;

    public static synchronized UserServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public Collection<UserDto> list() {
        return repo.list().stream().map(UserMapper.INSTANCE::toUserDto)
                .toList();
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return repo.findById(id).map(UserMapper.INSTANCE::toUserDto);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserDto update(User obj) {
        return null;
    }

    @Override
    public UserDto create(User obj) {
        return UserMapper.INSTANCE.toUserDto(repo.create(obj));
    }
}
