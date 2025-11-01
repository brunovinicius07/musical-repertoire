package com.music.model.exceptions.login;

import com.music.infra.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class EmailPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Email já cadastrado!";

    public EmailPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}