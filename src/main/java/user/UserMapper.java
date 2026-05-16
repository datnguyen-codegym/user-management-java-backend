package user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import user.dto.UserDto;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);
}
