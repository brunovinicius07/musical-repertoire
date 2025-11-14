package com.music.model.exceptions.password;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class NewPasswordNoMatchException extends AlertException {
    private static final String DEFAULT_MESSAGE = "As novas senhas não coincidem.";
    public NewPasswordNoMatchException() {
        super("400", DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
