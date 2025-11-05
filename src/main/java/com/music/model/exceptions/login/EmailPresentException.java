package com.music.model.exceptions.login;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class EmailPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Confira as suas informações!";

    public EmailPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}