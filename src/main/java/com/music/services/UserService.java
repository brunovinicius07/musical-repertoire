package com.music.services;

import com.music.model.dto.request.UpdateUserRequest;
import com.music.model.dto.response.UserResponseDto;
import com.music.model.entity.User;

public interface UserService {

    UserResponseDto updateUser(Long idUser, UpdateUserRequest updateUserRequest);

    String deleteUser(Long idUser);

    User validateUser(Long idUser);

}
