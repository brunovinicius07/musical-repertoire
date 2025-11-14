package com.music.model.exceptions.user;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Email não localizado";
    public EmailNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
