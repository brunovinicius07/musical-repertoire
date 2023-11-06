package com.music.model.mapper;

import com.music.authentication.auth.RegisterRequest;
import com.music.model.dto.response.UserResponseDto;
import com.music.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "nmUser", source = "nmUser")
    User registerDtoToUser(RegisterRequest registerDto);


    UserResponseDto userToDto(User user);
}
