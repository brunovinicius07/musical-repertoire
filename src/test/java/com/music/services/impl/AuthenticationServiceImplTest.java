package com.music.services.impl;

import com.music.infra.security.TokenService;
import com.music.model.dto.request.AuthenticationRequest;
import com.music.model.dto.request.RegisterRequest;
import com.music.model.dto.response.AuthenticationResponse;
import com.music.model.entity.User;
import com.music.model.exceptions.login.EmailPresentException;
import com.music.model.exceptions.login.VerifyCredential;
import com.music.model.exceptions.authentication.NotFoundException;
import com.music.model.mapper.UserMapper;
import com.music.repositories.UserRepository;
import com.music.role.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authRequest;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest(
                "User71", "user71@email.com", "12345678", UserRole.ADMIN);
        authRequest = new AuthenticationRequest("user71@email.com", "12345678");
        user = User.builder()
                .idUser(1L)
                .nameUser("User71")
                .email("user71@email.com")
                .password("12345678")
                .role(UserRole.USER)
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(userMapper.registerDtoToUser(registerRequest)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(tokenService.generateToken(user)).thenReturn("A1f45Cmd3421oU");
        when(userMapper.userToAuthenticationResponse(user))
                .thenReturn(new AuthenticationResponse(null, 1L, "User71", UserRole.USER));

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("A1f45Cmd3421oU", response.getToken());
        verify(userRepository).save(user);
        verify(passwordEncoder).encode("12345678");
    }

    @Test
    void shouldThrowEmailPresentException_WhenEmailAlreadyExists() {
        when(userRepository.findByEmail(registerRequest.getEmail()))
                .thenReturn(Optional.of(user));

        assertThrows(EmailPresentException.class, () -> authenticationService.register(registerRequest));
    }

    @Test
    void shouldLoginSuccessfully() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("A1f45Cmd3421oU");
        when(userMapper.userToAuthenticationResponse(user))
                .thenReturn(new AuthenticationResponse(null, 1L, "User71", UserRole.USER));

        AuthenticationResponse response = authenticationService.login(authRequest);

        assertNotNull(response);
        assertEquals("A1f45Cmd3421oU", response.getToken());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void shouldThrowVerifyCredential_WhenBadCredentials() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(VerifyCredential.class, () -> authenticationService.login(authRequest));
    }

    @Test
    void shouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = authenticationService.validateUserById(1L);

        assertEquals(user, found);
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowUserNotFound_WhenUserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authenticationService.validateUserById(99L));
    }
}
