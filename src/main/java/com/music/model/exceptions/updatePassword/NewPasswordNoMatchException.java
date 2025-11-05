package com.music.model.exceptions.updatePassword;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class NewPasswordNoMatchException extends AlertException {
    private static final String DEFAULT_MESSAGE = "As novas senhas não coinvidem.";
    public NewPasswordNoMatchException() {
        super("400", DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
