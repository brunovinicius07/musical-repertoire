package com.music.model.mapper;

import com.music.infra.auth.AuthenticationResponse;
import com.music.infra.auth.RegisterRequest;
import com.music.model.dto.response.UserResponseDto;
import com.music.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "nameUser", source = "nameUser")
    User registerDtoToUser(RegisterRequest registerDto);

    UserResponseDto userToResponseDto(User user);

    AuthenticationResponse userToAuthenticationResponse(User user);
}
