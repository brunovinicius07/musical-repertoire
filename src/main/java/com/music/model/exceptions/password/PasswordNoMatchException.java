package com.music.model.exceptions.password;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class PasswordNoMatchException extends AlertException {
    private static final String DEFAULT_MESSAGE = "As senhas não coincidem.";
    public PasswordNoMatchException() {
        super("400", DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
