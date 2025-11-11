package com.music.services.impl;

import com.music.infra.kafka.KafkaProducerService;
import com.music.infra.security.TokenService;
import com.music.model.dto.request.AuthenticationRequest;
import com.music.model.dto.request.ForgotPasswordRequest;
import com.music.model.dto.request.RegisterRequest;
import com.music.model.dto.request.ResetPasswordRequest;
import com.music.model.dto.response.AuthenticationResponse;
import com.music.model.entity.User;
import com.music.model.event.ForgotPasswordEvent;
import com.music.model.exceptions.login.EmailPresentException;
import com.music.model.exceptions.login.VerifyCredential;
import com.music.model.exceptions.password.NewPasswordNoMatchException;
import com.music.model.exceptions.token.TokenNotFoundOrExpiredException;
import com.music.model.exceptions.user.EmailNotFoundException;
import com.music.model.exceptions.user.UserNotFoundException;
import com.music.model.mapper.UserMapper;
import com.music.repositories.UserRepository;
import com.music.role.UserRole;
import com.music.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final KafkaProducerService producer;

    @Value("${api.security.token.timeExpirationToChangePassword}")
    private long timeExpirationToChangePassword;

    @Transactional(readOnly = false)
    public AuthenticationResponse register(RegisterRequest request) {

        existingUser(request.getEmail());

        User user = this.userMapper.registerDtoToUser(request);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ADMIN);

        userRepository.save(user);

        String token = tokenService.generateToken(user);
        AuthenticationResponse authenticationResponse = userMapper.userToAuthenticationResponse(user);
        authenticationResponse.setToken(token);

        return authenticationResponse;
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

    @Transactional
    public String forgotPassword(ForgotPasswordRequest request) {
        String message = "Se o e-mail: " + request.getEmail()
                + " estiver cadastrado, enviaremos instruções para redefinir sua senha.";

        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            String token = tokenService.generateTokenToChangePassword(user, timeExpirationToChangePassword);

            ForgotPasswordEvent event = new ForgotPasswordEvent(
                    user.getEmail(),
                    user.getNameUser(),
                    token
            );
            producer.sendForgotPasswordEvent(event);
        });

        return message;
    }

    @Transactional
    public String resetPassword(ResetPasswordRequest request) {
        if (!tokenService.isSimpleTokenValid(request.getToken())) {
            throw new TokenNotFoundOrExpiredException();
        }

        String email = tokenService.extractUsername(request.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);

        if(request.getNewPassword().equals(request.getConfirmNewPassword())){
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } else throw new NewPasswordNoMatchException();

        return "Senha redefinida com sucesso, faça login!";
    }

    @Transactional(readOnly = true)
    public void existingUser(String email){
        userRepository.findByEmail(email).ifPresent(verify -> {
            throw new EmailPresentException();
        });
    }

    @Transactional(readOnly = true)
    public User validateUserById(Long IdUser) {
        return userRepository.findById(IdUser).orElseThrow(UserNotFoundException::new);
    }

}
