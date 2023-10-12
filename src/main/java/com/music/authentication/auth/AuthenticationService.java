package com.music.authentication.auth;

import com.music.authentication.config.JwtService;
import com.music.exception.AlertException;
import com.music.model.entity.User;
import com.music.model.mapper.UserMapper;
import com.music.repositories.UserRepository;
import com.music.role.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Transactional(readOnly = false)
    public AuthenticationResponse register(RegisterRequest request) {

        User user = this.userMapper.registerDtoToUser(request);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        if (user.getAuthorities() != null) {
            this.saveEntity(user);
        }

        String token = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setCdUser(user.getCdUser());
        authenticationResponse.setToken(token);
        return authenticationResponse;
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String token = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setCdUser(user.getCdUser());

        return authenticationResponse;
    }

    @Transactional(readOnly = true)
    public Optional<User> userIsInvalid(String email) {
        Optional<User> newUser = this.userRepository.findByEmail(email);

        if (newUser.isPresent()) {
            throw new AlertException(
                    "warn",
                    String.format("Email já cadastrado", email),
                    HttpStatus.CONFLICT
            );
        }

        return newUser;
    }

    @Transactional(readOnly = true)
    public User validateEmailUser(String email) {

        Optional<User> optionalUser = this.userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new AlertException(
                    "warn",
                    String.format("Esse email %S está inválido ou não está cadastrado!", email),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalUser.get();
    }

    @Transactional(readOnly = false)
    public void saveEntity(User user) {
        var role = user.getAuthorities().stream().toList().get(0).toString();

        if (role.equals(UserRole.ADMIN.getRoleName())) {
            user.setRole(UserRole.ADMIN);

            this.userRepository.save(user);
        } else if (role.equals(UserRole.USER.getRoleName())) {
            user.setRole(UserRole.USER);

            this.userRepository.save(user);
        }
    }

}
