package com.music.model.exceptions.password;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class CurrentPasswordWrongException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Senha atual incorreta.";

    public CurrentPasswordWrongException() {
        super("401", DEFAULT_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}