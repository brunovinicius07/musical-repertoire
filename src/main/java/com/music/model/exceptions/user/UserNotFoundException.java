package com.music.model.exceptions.user;

import com.music.infra.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Email ou senha incorretos!";
    public UserNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
