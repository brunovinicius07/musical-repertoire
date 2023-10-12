package com.music.authentication.auth;

import com.music.authentication.config.JwtService;
import com.music.model.entity.User;
import com.music.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("v1/music/auth")
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService service, UserRepository userRepository, AuthenticationManager authenticationManager, AuthenticationService authenticationService, JwtService jwtService) {
        this.service = service;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request) {

        authenticationService.userIsInvalid(request.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}
