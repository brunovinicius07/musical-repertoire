package com.music.model.exceptions.login;

import com.music.authentication.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class VerifyCredential extends AlertException {
    private static final String DEFAULT_MESSAGE = "Email ou senha inválidos!";
    public VerifyCredential() {
        super("401", DEFAULT_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
