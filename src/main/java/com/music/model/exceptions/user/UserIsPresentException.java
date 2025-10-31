package com.music.model.exceptions.user;

import com.music.authentication.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class UserIsPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Email já existe!";

    public UserIsPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}