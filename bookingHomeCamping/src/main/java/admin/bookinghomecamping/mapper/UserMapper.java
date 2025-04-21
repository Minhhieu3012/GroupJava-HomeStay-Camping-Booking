package admin.bookinghomecamping.mapper;

import admin.bookinghomecamping.dto.request.UserCreationRequest;
import admin.bookinghomecamping.dto.request.UserUpdateRequest;
import admin.bookinghomecamping.dto.response.UserResponse;
import admin.bookinghomecamping.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}