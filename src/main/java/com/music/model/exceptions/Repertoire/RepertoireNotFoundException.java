package com.music.model.exceptions.Repertoire;

import com.music.authentication.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class RepertoireNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Repertorio não localizado";
    public RepertoireNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
