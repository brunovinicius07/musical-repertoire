package com.music.services.impl;

import com.music.infra.security.TokenService;
import com.music.model.dto.request.AuthenticationRequest;
import com.music.model.dto.request.RegisterRequest;
import com.music.model.dto.response.AuthenticationResponse;
import com.music.model.entity.User;
import com.music.model.exceptions.login.EmailPresentException;
import com.music.model.exceptions.login.VerifyCredential;
import com.music.model.exceptions.user.UserNotFoundException;
import com.music.model.mapper.UserMapper;
import com.music.repositories.UserRepository;
import com.music.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Transactional()
    public AuthenticationResponse register(RegisterRequest request) {

        existingUser(request.getEmail());

        User user = this.userMapper.registerDtoToUser(request);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        String token = tokenService.generateToken(user);
        AuthenticationResponse authenticationResponse = userMapper.userToAuthenticationResponse(user);
        authenticationResponse.setToken(token);

        return authenticationResponse;
    }

    @Transactional(readOnly = true)
    public void existingUser(String email){
        userRepository.findByEmail(email).ifPresent(verify -> {
            throw new EmailPresentException();
        });
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
           Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            String token = tokenService.generateToken(user);
            AuthenticationResponse authResponse = userMapper.userToAuthenticationResponse(user);
            authResponse.setToken(token);

            return authResponse;
        }catch (BadCredentialsException e){
            throw new VerifyCredential();
        }
    }

    @Transactional(readOnly = true)
    public User validateUserById(Long IdUser) {
        return userRepository.findById(IdUser).orElseThrow(UserNotFoundException::new);
    }

}
