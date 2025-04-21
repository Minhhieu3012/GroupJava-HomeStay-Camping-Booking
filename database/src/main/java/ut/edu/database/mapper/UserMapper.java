package ut.edu.database.mapper;

import ut.edu.database.dtos.UserDTO;
import ut.edu.database.models.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
}
