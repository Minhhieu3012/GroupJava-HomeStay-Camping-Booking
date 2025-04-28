package ut.edu.database.mapper;

import org.mapstruct.Mapping;
import ut.edu.database.dtos.UserDTO;
import ut.edu.database.models.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "avatar", target = "avatar")
    UserDTO toDTO(User user);

    @Mapping(source = "avatar", target = "avatar")
    User toEntity(UserDTO dto);
}
