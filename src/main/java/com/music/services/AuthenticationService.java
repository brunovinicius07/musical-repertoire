package com.music.services;

import com.music.model.dto.request.AuthenticationRequest;
import com.music.model.dto.request.RegisterRequest;
import com.music.model.dto.response.AuthenticationResponse;
import com.music.model.entity.User;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    void existingUser(String email);

    AuthenticationResponse login(AuthenticationRequest request);

    User validateUserById(Long IdUser);
}
